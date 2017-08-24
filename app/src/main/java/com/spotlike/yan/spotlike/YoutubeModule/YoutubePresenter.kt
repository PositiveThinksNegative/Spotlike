package com.spotlike.yan.spotlike.YoutubeModule

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.facebook.login.LoginManager
import com.spotlike.yan.spotlike.MainActivity
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.R
import javax.inject.Inject
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail.YoutubeDetailActivity


/**
 * Created by yan on 2017-08-16.
 */
class YoutubePresenter : YoutubeContract.YoutubePresenterContract, OnRecyclerViewItemClickListener {
    @Inject lateinit var routingManager: RoutingManager
    @Inject lateinit var youtubeRequestMngr: YoutubeRequestManager
    @Inject lateinit var context: Context
    private var youtubeView: YoutubeView? = null
    private var recyclerView: RecyclerView? = null
    private var youtubeList : ArrayList<YoutubeItem> = ArrayList()
    private var adapter: YoutubeAdapter? = null

    init {
        MainApplication.Companion.graph.inject(this)
        searchYoutube("test")
    }

    fun bind(youtubeView: YoutubeView, recyclerView: RecyclerView) {
        this.youtubeView = youtubeView
        this.recyclerView = recyclerView
    }

    override fun onViewCreated() {
        adapter = YoutubeAdapter(youtubeList)
        adapter?.setOnItemClickListener(this)
        recyclerView?.adapter = adapter

        var numberOfElements = 1
        if (routingManager.getGridRotationLayout() == GridLayoutManager.HORIZONTAL) {
            numberOfElements = 2
        }

        val gridLayoutManager = GridLayoutManager(context, numberOfElements, GridLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
    }

    override fun searchYoutube(searchKeyword: String) {
        val url = youtubeRequestMngr.constructYTSearchRequest(25, searchKeyword)
        youtubeRequestMngr.launchYTRequestFromUrl(url)
        youtubeRequestMngr.setListener(object: YoutubeRequestManager.YoutubeRequestListener {
            override fun youtubeResponse(youtubeItems: ArrayList<YoutubeItem>) {
                youtubeView?.getParentActivity()?.runOnUiThread {
                    youtubeList.clear()
                    youtubeList.addAll(youtubeItems)
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
            }
        })

    }

    override fun onRecyclerViewItemClicked(position: Int, youtubeItem: YoutubeItem, view: View?) {
        routingManager.startActivity(youtubeView?.activity, YoutubeDetailActivity().javaClass, youtubeItem.id.videoId, view?.findViewById(R.id.thumbnail))
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onOptionsItemSelected(item: MenuItem?, activity: Activity) : Boolean {
        when (item?.itemId) {
            R.id.facebook_logout_button -> {
                LoginManager.getInstance().logOut()
                activity.invalidateOptionsMenu()
                routingManager.startActivity(activity, MainActivity().javaClass)
                return true
            }
            else -> return false
        }
    }

    override fun unbind() {
        this.youtubeView = null
        this.recyclerView = null
    }
}
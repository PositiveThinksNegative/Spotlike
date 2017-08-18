package com.spotlike.yan.spotlike.YoutubeModule

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.facebook.login.LoginManager
import com.spotlike.yan.spotlike.MainActivity
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.R
import javax.inject.Inject
import android.support.v7.widget.GridLayoutManager



/**
 * Created by yan on 2017-08-16.
 */
class YoutubePresenter : YoutubeContract.YoutubePresenterContract {
    @Inject lateinit var routingManager: RoutingManager
    @Inject lateinit var youtubeRequestMngr: YoutubeRequestManager
    @Inject lateinit var context: Context
    private var youtubeView: YoutubeView? = null
    private var recyclerView: RecyclerView? = null
    private var layoutManager : LinearLayoutManager? = null
    private var youtubeList : ArrayList<YoutubeItem> = ArrayList()

    init {
        MainApplication.Companion.graph.inject(this)
        searchYoutube("test")
    }

    fun bind(youtubeView: YoutubeView, recyclerView: RecyclerView) {
        this.youtubeView = youtubeView
        this.recyclerView = recyclerView
        this.layoutManager = LinearLayoutManager(context)
    }

    override fun onViewCreated() {
        val adapter = YoutubeAdapter(youtubeList)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

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
            override fun youtubeResponse(youtubeObject: YoutubeObject) {
                youtubeView?.getParentActivity()?.runOnUiThread {
                    youtubeList.clear()
                    youtubeList.addAll(youtubeObject.items)
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
            }
        })

    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun unbind() {
        this.youtubeView = null
        this.recyclerView = null
        this.layoutManager = null
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
}
package com.spotlike.yan.spotlike.YoutubeModule

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import com.facebook.login.LoginManager
import com.spotlike.yan.spotlike.MainActivity
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.R
import javax.inject.Inject

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
    private var list : ArrayList<String> = ArrayList()


    init {
        MainApplication.Companion.graph.inject(this)
        for (i in 1..10) {
            list.add("Test $i")
        }
    }

    fun bind(youtubeView: YoutubeView, recyclerView: RecyclerView) {
        this.youtubeView = youtubeView
        this.recyclerView = recyclerView
        this.layoutManager = LinearLayoutManager(context)
    }

    override fun onViewCreated() {
        var adapter = YoutubeAdapter(list)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.remove(viewHolder.adapterPosition)
            }
        }

        val url = youtubeRequestMngr.constructYTSearchRequest(25, "Hearthstone")
        youtubeRequestMngr.launchYTRequestFromUrl(url)

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun unbind() {
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
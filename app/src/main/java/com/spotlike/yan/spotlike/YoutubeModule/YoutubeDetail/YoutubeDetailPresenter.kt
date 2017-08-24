package com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail

import android.app.Activity
import android.support.design.widget.AppBarLayout
import android.view.MenuItem
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.R
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeRequestManager
import javax.inject.Inject

/**
 * Created by yan on 2017-08-22.
 */
class YoutubeDetailPresenter: YoutubeDetailContract.YoutubeDetailPresentation {
    @Inject lateinit var youtubeRequestMngr: YoutubeRequestManager
    @Inject lateinit var routingManager: RoutingManager
    private var youtubeDetailView: YoutubeDetailContract.YoutubeDetailView? = null
    private var videoId: String = ""

    init {
        MainApplication.Companion.graph.inject(this)
    }

    fun bind(youtubeDetailView: YoutubeDetailContract.YoutubeDetailView, videoId: String) {
        this.youtubeDetailView = youtubeDetailView
        this.videoId = videoId
    }

    override fun onViewCreated() {
        youtubeRequestMngr.getYoutubeItem(videoId)?.let {
            youtubeDetailView?.setDescriptionText(it.snippet.description)
            youtubeDetailView?.setToolbarImage(it.snippet.thumbnails.medium.url)
            youtubeDetailView?.setToolbarTitle(it.snippet.title)
            youtubeDetailView?.setDescriptionTitle(it.snippet.title)
        }
    }

    override fun addOffsetListener(appBarLayout: AppBarLayout) {
        appBarLayout?.addOnOffsetChangedListener(object: AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (appBarLayout != null) {
                    if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                        youtubeDetailView?.showOption(R.id.action_play)
                        youtubeDetailView?.showToolbarTitle()
                        youtubeDetailView?.removeTitleDescription()
                    } else {
                        youtubeDetailView?.hideOption(R.id.action_play)
                        youtubeDetailView?.hideToolbarTitle()
                        youtubeDetailView?.showTitleDescription()
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?, activity: Activity): Boolean {
        when (item?.itemId) {
            R.id.action_play -> {
                onActionPlaySelected(activity)
                return true
            }
            else -> return false
        }
    }

    override fun onActionPlaySelected(activity: Activity) {
        routingManager.startActivity(activity, YoutubeVideoPlayer().javaClass, videoId)
    }

    override fun onResume() {

    }

    override fun onPause() {
    }

    override fun unbind() {
    }
}
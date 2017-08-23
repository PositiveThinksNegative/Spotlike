package com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail

import android.app.Activity
import android.support.design.widget.AppBarLayout
import android.view.MenuItem
import com.facebook.login.LoginManager
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
    private var appBar: AppBarLayout? = null

    init {
        MainApplication.Companion.graph.inject(this)
    }

    fun bind(youtubeDetailView: YoutubeDetailContract.YoutubeDetailView, videoId: String, appBarLayout: AppBarLayout) {
        this.youtubeDetailView = youtubeDetailView
        this.videoId = videoId
        this.appBar = appBarLayout
    }

    override fun onViewCreated() {
        appBar?.addOnOffsetChangedListener(object: AppBarLayout.OnOffsetChangedListener{
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (appBarLayout != null) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.totalScrollRange
                    }
                    if (scrollRange + verticalOffset == 0) {
                        isShow = true
                        youtubeDetailView?.showOption(R.id.action_play)
                        youtubeDetailView?.showToolbarTitle()
                        youtubeDetailView?.hideTitleDescription()
                    } else if (isShow) {
                        isShow = false
                        youtubeDetailView?.hideOption(R.id.action_play)
                        youtubeDetailView?.hideToolbarTitle()
                        youtubeDetailView?.showTitleDescription()
                    }
                }
            }
        })
        youtubeRequestMngr.getYoutubeItem(videoId)?.let {
            youtubeDetailView?.setDescriptionText(it.snippet.description)
            youtubeDetailView?.setToolbarImage(it.snippet.thumbnails.high.url)
            youtubeDetailView?.setToolbarTitle(it.snippet.title)
            youtubeDetailView?.setDescriptionTitle(it.snippet.title)
        }
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
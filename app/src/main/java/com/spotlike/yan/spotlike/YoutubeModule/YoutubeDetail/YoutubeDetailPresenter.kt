package com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail

import android.support.design.widget.AppBarLayout
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.ImageManager
import com.spotlike.yan.spotlike.R
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeRequestManager
import javax.inject.Inject

/**
 * Created by yan on 2017-08-22.
 */
class YoutubeDetailPresenter: YoutubeDetailContract.YoutubeDetailPresentation {
    @Inject lateinit var youtubeRequestMngr: YoutubeRequestManager
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
                    } else if (isShow) {
                        isShow = false
                        youtubeDetailView?.hideOption(R.id.action_play)
                    }
                }
            }
        })
        youtubeRequestMngr.getYoutubeItem(videoId)?.let {
            youtubeDetailView?.setDescriptionText(it.snippet.description)
            youtubeDetailView?.setToolbarImage(it.snippet.thumbnails.high.url)
            youtubeDetailView?.setToolbarTitle(it.snippet.title)
        }
    }

    override fun onResume() {

    }

    override fun onPause() {
    }

    override fun unbind() {
    }
}
package com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.R
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeRequestManager
import kotlinx.android.synthetic.main.youtube_video_player.*

/**
 * Created by yan on 2017-08-23.
 */
class YoutubeVideoPlayer : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private var videoId : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.youtube_video_player)

        videoId = intent.extras.getString(RoutingManager.EXTRA_STRING)
        youtube_video_player.initialize(YoutubeRequestManager.keyValue, this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        if(!wasRestored) {
            player?.cueVideo(videoId)
            player?.setFullscreen(true)
            player?.play()
        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

    }
}
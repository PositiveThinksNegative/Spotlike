package com.spotlike.yan.spotlike

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.AccessToken
import com.spotlike.yan.spotlike.FacebookModule.FacebookView
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var routingManager: RoutingManager

    init {
        MainApplication.Companion.graph.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (AccessToken.getCurrentAccessToken() == null) {
            routingManager.replaceFragment(R.id.fragment_frame_layout, Bundle(), FacebookView(), this)
        } else {
            routingManager.startActivity(this, YoutubeView().javaClass)
        }
    }
}

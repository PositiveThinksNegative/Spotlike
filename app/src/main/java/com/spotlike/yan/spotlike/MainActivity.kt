package com.spotlike.yan.spotlike

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.facebook.AccessToken
import com.spotlike.yan.spotlike.FacebookModule.FacebookView
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.YoutubeModule.YoutubeView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var routingManager: RoutingManager

    init {
        MainApplication.Companion.graph.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        if (isFacebookLoggedIn()) {
            inflater.inflate(R.menu.youtube_menu, menu)
        } else {
            inflater.inflate(R.menu.main_menu, menu)
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_activity_toolbar)
        if (isFacebookLoggedIn()) {
            routingManager.replaceFragment(R.id.fragment_frame_layout, Bundle(), YoutubeView(), this)
        } else {
            routingManager.replaceFragment(R.id.fragment_frame_layout, Bundle(), FacebookView(), this)
        }
    }

    private fun isFacebookLoggedIn() : Boolean {
        return AccessToken.getCurrentAccessToken() != null
    }
}

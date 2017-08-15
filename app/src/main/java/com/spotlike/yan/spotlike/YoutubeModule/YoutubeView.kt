package com.spotlike.yan.spotlike.YoutubeModule

import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.facebook.login.LoginManager
import com.spotlike.yan.spotlike.MainActivity
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.R
import javax.inject.Inject
import android.view.MenuInflater



/**
 * Created by yan on 2017-08-15.
 */
class YoutubeView : AppCompatActivity() {
    @Inject lateinit var routingManager: RoutingManager

    init {
        MainApplication.Companion.graph.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.youtube_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.facebook_logout_button -> {
                LoginManager.getInstance().logOut()
                routingManager.startActivity(this, MainActivity().javaClass)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
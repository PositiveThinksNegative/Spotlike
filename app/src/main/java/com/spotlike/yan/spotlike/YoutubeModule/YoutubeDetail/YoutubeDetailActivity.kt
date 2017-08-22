package com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.ImageManager
import com.spotlike.yan.spotlike.Managers.RoutingManager
import com.spotlike.yan.spotlike.R
import kotlinx.android.synthetic.main.activity_youtube_detail.*
import kotlinx.android.synthetic.main.content_youtube_detail.*
import javax.inject.Inject

class YoutubeDetailActivity : AppCompatActivity(), YoutubeDetailContract.YoutubeDetailView {
    @Inject lateinit var youtubeDetailPresenter: YoutubeDetailPresenter
    @Inject lateinit var imageManager: ImageManager
    private var menu: Menu? = null

    init {
        MainApplication.Companion.graph.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val videoId = intent.extras.getString(RoutingManager.EXTRA_STRING)
        youtubeDetailPresenter.bind(this, videoId, app_bar)
        youtubeDetailPresenter.onViewCreated()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_youtube_detail, menu)
        hideOption(R.id.action_play)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun getParentActivity(): AppCompatActivity {
        return this
    }

    override fun showOption(id: Int) {
        menu?.findItem(id)?.setVisible(true)
    }

    override fun hideOption(id: Int) {
        menu?.findItem(id)?.setVisible(false)
    }

    override fun setToolbarImage(imageSource: String) {
        imageManager.loadImage(imageSource, expanded_image)
    }

    override fun setDescriptionText(text: String) {
        youtube_detail_content.text = text
    }

    override fun setToolbarTitle(title: String) {
        toolbar_layout.title = title
    }

}

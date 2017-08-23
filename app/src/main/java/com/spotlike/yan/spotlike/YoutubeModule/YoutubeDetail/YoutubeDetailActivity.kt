package com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
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
        supportActionBar?.setDisplayShowTitleEnabled(false)
        hideToolbarTitle()
        window.allowEnterTransitionOverlap = false

        val fade = Explode()
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        window.enterTransition = fade

        val videoId = intent.extras.getString(RoutingManager.EXTRA_STRING)
        youtubeDetailPresenter.bind(this, videoId, app_bar)
        youtubeDetailPresenter.onViewCreated()

        action_button_play.setOnClickListener {
            youtubeDetailPresenter.onActionPlaySelected(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_youtube_detail, menu)
        hideOption(R.id.action_play)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var optionHandled = youtubeDetailPresenter.onOptionsItemSelected(item, this)
        if(optionHandled) {
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
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

    override fun showToolbarTitle() {
        toolbar_layout.isTitleEnabled = true
    }

    override fun hideToolbarTitle() {
        toolbar_layout.isTitleEnabled = false
    }

    override fun showTitleDescription() {
        youtube_detail_title.visibility = TextView.VISIBLE
    }

    override fun hideTitleDescription() {
        youtube_detail_title.visibility = TextView.GONE
    }

    override fun setToolbarImage(imageSource: String) {
        imageManager.loadImage(imageSource, thumbnail)
    }

    override fun setDescriptionText(text: String) {
        youtube_detail_content.text = text
    }

    override fun setToolbarTitle(title: String) {
        toolbar_layout?.title = title
    }

    override fun setDescriptionTitle(title: String) {
        youtube_detail_title?.text = title
    }
}

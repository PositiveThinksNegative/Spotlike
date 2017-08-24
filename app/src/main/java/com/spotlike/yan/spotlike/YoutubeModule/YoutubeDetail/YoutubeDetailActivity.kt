package com.spotlike.yan.spotlike.YoutubeModule.YoutubeDetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.transition.Transition
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        hideToolbarTitle()
        hideActionButton()
        postponeEnterTransition()

        thumbnail.transitionName = intent.extras.getString(RoutingManager.TRANSITION_STRING)
        window.enterTransition = Explode()
        window.sharedElementEnterTransition.addListener(object: Transition.TransitionListener{
            override fun onTransitionEnd(p0: Transition?) {
                window.sharedElementEnterTransition.removeListener(this)
                youtubeDetailPresenter.addOffsetListener(app_bar)
                showActionButton()
            }

            override fun onTransitionResume(p0: Transition?) { }

            override fun onTransitionPause(p0: Transition?) { }

            override fun onTransitionCancel(p0: Transition?) { }

            override fun onTransitionStart(p0: Transition?) { }
        })

        val videoId = intent.extras.getString(RoutingManager.EXTRA_STRING)
        youtubeDetailPresenter.bind(this, videoId)
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
        val optionHandled = youtubeDetailPresenter.onOptionsItemSelected(item, this)
        return if (optionHandled) true else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        hideActionButton()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun getParentActivity(): AppCompatActivity {
        return this
    }

    override fun showOption(id: Int) {
        menu?.findItem(id)?.isVisible = true
    }

    override fun hideOption(id: Int) {
        menu?.findItem(id)?.isVisible = false
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
        youtube_detail_title.visibility = TextView.INVISIBLE
    }

    override fun removeTitleDescription() {
        youtube_detail_title.visibility = TextView.GONE
    }

    override fun showActionButton() {
        action_button_play.visibility = View.VISIBLE
    }

    override fun hideActionButton() {
        action_button_play.visibility = View.GONE
    }

    override fun setToolbarImage(imageSource: String) {
        val requestListener : RequestListener<Drawable> = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }

        }
        imageManager.loadImageWithRequest(imageSource, thumbnail, requestListener)
    }

    override fun setDescriptionText(text: String) {
        youtube_detail_content.text = text
    }

    override fun setToolbarTitle(title: String) {
        toolbar_layout.title = title
    }

    override fun setDescriptionTitle(title: String) {
        youtube_detail_title.text = title
    }
}

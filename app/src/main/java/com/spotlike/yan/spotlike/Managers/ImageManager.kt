package com.spotlike.yan.spotlike.Managers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.spotlike.yan.spotlike.MainApplication
import javax.inject.Inject

/**
 * Created by yan on 2017-08-18.
 */
class ImageManager private constructor() {
    @Inject lateinit var context: Context
    private lateinit var glide : RequestManager

    companion object {
        val INSTANCE: ImageManager by lazy {
            ImageManager()
        }
    }

    init {
        MainApplication.Companion.graph.inject(this)
        glide = Glide.with(context)
    }

    fun loadImage(url: String, imageView: ImageView) {
        glide.load(url).into(imageView)
    }
}
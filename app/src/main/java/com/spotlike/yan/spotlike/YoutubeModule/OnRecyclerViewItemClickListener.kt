package com.spotlike.yan.spotlike.YoutubeModule

import android.view.View

/**
 * Created by yan on 2017-08-21.
 */
interface OnRecyclerViewItemClickListener {
    fun onRecyclerViewItemClicked(position: Int, youtubeItem: YoutubeItem, view: View?)
}
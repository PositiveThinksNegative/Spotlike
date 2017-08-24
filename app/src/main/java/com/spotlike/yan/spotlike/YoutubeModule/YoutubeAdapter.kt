package com.spotlike.yan.spotlike.YoutubeModule

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.ImageManager
import com.spotlike.yan.spotlike.R
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import javax.inject.Inject

/**
 * Created by yan on 2017-08-16.
 */
class YoutubeAdapter(private val values : ArrayList<YoutubeItem>): RecyclerView.Adapter<YoutubeAdapter.ViewHolder>() {
    @Inject lateinit var imageManager: ImageManager
    private var listener: OnRecyclerViewItemClickListener? = null
    private var mLastClickTime = System.currentTimeMillis()
    private val CLICK_TIME_INTERVAL: Long = 400

    init {
        MainApplication.graph.inject(this)
    }

    class ViewHolder : RecyclerView.ViewHolder {
        var textHeader : TextView
        var textFooter : TextView
        var imageView : ImageView
        var layout : View

        constructor(itemView: View) : super(itemView) {
            layout = itemView.card_relative_view
            imageView = layout.thumbnail
            textHeader = layout.title
            textFooter = layout.channel
        }
    }


    fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener) {
        this.listener = listener
    }

    fun add(position: Int, item: YoutubeItem) {
        this.values.add(position, item)
    }

    fun remove(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, getItemCount())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var youtubeItem : YoutubeItem = values.get(position)
        imageManager.loadImage(youtubeItem.snippet.thumbnails.medium.url, holder.imageView)
        ViewCompat.setTransitionName(holder.imageView, youtubeItem.id.videoId)
        holder.textHeader.text = youtubeItem.snippet.title
        holder.textFooter.text = youtubeItem.snippet.channelTitle
        holder.layout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val now = System.currentTimeMillis()
                if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                    return
                }
                mLastClickTime = now

                view?.isClickable = false
                listener?.onRecyclerViewItemClicked(holder.adapterPosition, youtubeItem, view)
                view?.isClickable = true
            }
        })
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        var view : View = inflater.inflate(R.layout.recyclerview_item_row, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }
}
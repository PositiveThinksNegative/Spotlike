package com.spotlike.yan.spotlike.YoutubeModule

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.spotlike.yan.spotlike.MainApplication
import com.spotlike.yan.spotlike.Managers.ImageManager
import com.spotlike.yan.spotlike.R
import javax.inject.Inject

/**
 * Created by yan on 2017-08-16.
 */
class YoutubeAdapter(private val values : ArrayList<YoutubeItem>): RecyclerView.Adapter<YoutubeAdapter.ViewHolder>() {
    @Inject lateinit var imageManager: ImageManager

    init {
        MainApplication.graph.inject(this)
    }

    class ViewHolder : RecyclerView.ViewHolder {
        var textHeader : TextView
        var textFooter : TextView
        var imageView : ImageView
        var layout : View

        constructor(itemView: View) : super(itemView) {
            layout = itemView
            imageView = layout.findViewById(R.id.thumbnail)
            textHeader = layout.findViewById(R.id.title)
            textFooter = layout.findViewById(R.id.channel)
        }
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
        holder.textHeader.text = youtubeItem.snippet.title
        holder.textFooter.text = youtubeItem.snippet.channelTitle
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
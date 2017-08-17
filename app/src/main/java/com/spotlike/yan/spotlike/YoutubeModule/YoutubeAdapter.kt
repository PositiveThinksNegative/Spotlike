package com.spotlike.yan.spotlike.YoutubeModule

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.spotlike.yan.spotlike.R

/**
 * Created by yan on 2017-08-16.
 */
class YoutubeAdapter(private val values : ArrayList<YoutubeItem>): RecyclerView.Adapter<YoutubeAdapter.ViewHolder>() {

    class ViewHolder : RecyclerView.ViewHolder {
        var textHeader : TextView
        var textFooter : TextView
        var layout : View

        constructor(itemView: View) : super(itemView) {
            layout = itemView
            textHeader = layout.findViewById(R.id.firstLine)
            textFooter = layout.findViewById(R.id.secondLine)
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
        holder.textHeader.text = youtubeItem.snippet.title
        holder.textFooter.text = youtubeItem.snippet.description
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater : LayoutInflater = LayoutInflater.from(parent.context)
        var view : View = inflater.inflate(R.layout.recyclerview_item_row, parent, false)
        var viewHolder : ViewHolder = ViewHolder(view)
        return viewHolder
    }
}
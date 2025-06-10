package com.example.contentprovider.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.contentprovider.R
import com.example.contentprovider.domain.ImageMedia


class ImageMediaGridAdapter(private val context: Context, private var mediaList: List<ImageMedia> = arrayListOf()) : BaseAdapter() {
    override fun getCount() = mediaList.size
    override fun getItem(position: Int) = mediaList[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_media, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        Glide.with(context)
            .load(mediaList[position].uri)
            .into(imageView)

        return view
    }
    fun setData(list: List<ImageMedia>) {
        this.mediaList = list
        notifyDataSetChanged()
    }
}

package com.example.sho.myportalapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.sho.myportalapp.R
import com.example.sho.myportalapp.googleNewsApi.ArticleSource
import com.squareup.picasso.Picasso
import java.util.*

/**
 * NewsRecyclerAdapter
 *
 * Created by sho on 2019/09/07.
 */

class NewsRecyclerAdapter(private val adapterContext: Context, itemList: ArrayList<ArticleSource>) :
        RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {

    private val itemList: List<ArticleSource>
    private var inflater: LayoutInflater? = null

    init {
        inflater = LayoutInflater.from(adapterContext)
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater?.inflate(R.layout.news_adapter_card_list, parent, false)
                ?: parent
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsRecyclerAdapter.ViewHolder, position: Int) {

        val imageUri = itemList[position].getActualImageUrl()

        holder.titleTextView.text = itemList[position].title
        holder.urlTextView.text = itemList[position].url
        holder.newsContentSummaryTextView.text = itemList[position].description

        try {
            Picasso.with(adapterContext).load(imageUri)
                    .placeholder(R.drawable.news_pic).into(holder.urlImageView)
        } catch (e: Exception) {
            holder.urlImageView.setImageResource(R.drawable.news_pic)
        }
        holder.setNewsUrl(itemList[position].url)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val titleTextView: TextView = view.findViewById(R.id.news_title_name)
        internal val urlTextView: TextView = view.findViewById(R.id.news_url_content)
        internal val newsContentSummaryTextView: TextView = view.findViewById(R.id.news_content_summary)
        internal val urlImageView: ImageView = view.findViewById(R.id.news_thumbnail)

        fun setNewsUrl(url: String?) {
            itemView.setOnClickListener { view ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }
}

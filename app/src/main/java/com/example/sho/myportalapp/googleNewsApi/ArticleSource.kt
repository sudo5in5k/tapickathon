package com.example.sho.myportalapp.googleNewsApi

import android.net.Uri

/**
 * Source
 *
 * Created by sho on 2019/09/07.
 */
data class ArticleSource(
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?
) {
    fun getActualImageUrl():Uri? {
        urlToImage?.apply {
            return Uri.parse(this)
        }
        return null
    }
}
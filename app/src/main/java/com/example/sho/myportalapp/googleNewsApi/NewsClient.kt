package com.example.sho.myportalapp.googleNewsApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Client
 * @see: https://newsapi.org/docs
 *
 * Created by sho on 2019/9/7.
 */
interface NewsClient {
    @GET("everything")
    fun addQuery(@Query("q") q: String,
                 @Query("apiKey") key: String,
                 @Query("sortBy") sortBy: String,
                 @Query("pageSize") pageSize: Int): Call<NewsSource>

}
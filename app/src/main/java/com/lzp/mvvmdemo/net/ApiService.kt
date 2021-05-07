package com.lzp.mvvmdemo.net

import com.lzp.mvvmdemo.model.News
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("news/before/{date}")
    suspend fun getNews(@Path("date") date: String): News

    companion object {
        private val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        fun create(): ApiService = apiService
    }
}
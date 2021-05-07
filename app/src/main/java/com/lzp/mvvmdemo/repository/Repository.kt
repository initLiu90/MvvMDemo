package com.lzp.mvvmdemo.repository

import com.lzp.mvvmdemo.model.News
import com.lzp.mvvmdemo.net.ApiService

class Repository private constructor() {
    companion object {
        private val repository by lazy { Repository() }
        fun getInstance(): Repository = repository
    }

    suspend fun fetchNews(date: String): News {
        return ApiService.create().getNews(date)
    }
}
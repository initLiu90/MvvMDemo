package com.lzp.mvvmdemo.model

data class News(val date: String?, val stories: List<Story>?)
data class Story(val title: String?, val url: String?, val hint: String?, val images: List<String>?)
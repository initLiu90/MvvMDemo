package com.lzp.mvvmdemo.news.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lzp.mvvmdemo.common.ItemViewModel
import com.lzp.mvvmdemo.model.Story

private const val UNLIKE = "Unlike"
private const val LIKE = "Like"

class NewsItemViewModel(private val story: Story) : ItemViewModel {

    val title: String
        get() = story.title ?: ""

    private val _like = MutableLiveData("Unlike")
    val like: LiveData<String> = _like

    val coverUrl: String
        get() = story.images?.firstOrNull() ?: ""

    private val _showLoading = MutableLiveData<Boolean>(true)
    val showLoading: LiveData<Boolean> = _showLoading

    fun onCoverReady() {
        _showLoading.value = false
    }

    fun handleLike() {
        _like.value = when (_like.value) {
            UNLIKE -> LIKE
            LIKE -> UNLIKE
            else -> UNLIKE
        }
    }
}
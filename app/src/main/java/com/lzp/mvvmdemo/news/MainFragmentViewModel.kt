package com.lzp.mvvmdemo.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lzp.mvvmdemo.common.ItemViewModel
import com.lzp.mvvmdemo.model.News
import com.lzp.mvvmdemo.news.model.FooterItemViewMode
import com.lzp.mvvmdemo.news.model.HeaderItemViewModel
import com.lzp.mvvmdemo.news.model.NewsItemViewModel
import com.lzp.mvvmdemo.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainFragmentViewModel : ViewModel() {
    private val _news = MutableLiveData<List<ItemViewModel>>()
    val news: LiveData<List<ItemViewModel>> = _news

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val news = Repository.getInstance()
                    .fetchNews("20210430")
                _news.value = formatData(news)
            } catch (ex: HttpException) {
                Log.e(
                    "Test",
                    "request ${
                        ex.response()?.raw()?.request()?.url()
                    } error, code: ${ex.code()}, message: ${ex.message()}"
                )
            }
        }
    }

    private fun formatData(news: News): List<ItemViewModel> {
        val items = mutableListOf<ItemViewModel>(HeaderItemViewModel())
        val data = news?.stories?.map { NewsItemViewModel(it) } ?: mutableListOf()
        items.addAll(data)
        items.add(FooterItemViewMode())
        return items
    }
}
package com.lzp.mvvmdemo.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lzp.mvvmdemo.common.ItemViewModel
import com.lzp.mvvmdemo.model.Story
import com.lzp.mvvmdemo.news.viewmodels.FooterItemViewMode
import com.lzp.mvvmdemo.news.viewmodels.HeaderItemViewModel
import com.lzp.mvvmdemo.news.viewmodels.NewsItemViewModel
import com.lzp.mvvmdemo.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsFragmentViewModel : ViewModel() {
    private val _news = MutableLiveData<List<ItemViewModel>>()
    val news: LiveData<List<ItemViewModel>> = _news

    private val date = MutableLiveData<String>()

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val data = Repository.getInstance()
                    .fetchNews("20210430")

                _news.value = createItemViewModels(data.stories)
                date.value = data.date ?: ""
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

    private fun createItemViewModels(stories: List<Story>?): List<ItemViewModel> {
        val itemViewModels = mutableListOf<ItemViewModel>(HeaderItemViewModel())
        itemViewModels.addAll(stories?.map { NewsItemViewModel(it) } ?: listOf())
        itemViewModels.add(FooterItemViewMode())
        return itemViewModels
    }

}
package com.lzp.mvvmdemo.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lzp.mvvmdemo.model.Story
import com.lzp.mvvmdemo.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainFragmentViewModel : ViewModel() {
    private val _news = MutableLiveData<List<Story>>()
    val news: LiveData<List<Story>> = _news

    private val _likesNum = MutableLiveData(0)
    val likesNum: LiveData<Int> = _likesNum

    private val date = MutableLiveData<String>()

    fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = Repository.getInstance()
                    .fetchNews("20210430")

                _news.postValue(data.stories ?: listOf())
                date.postValue(data.date ?: "")
            } catch (ex: HttpException) {
                Log.e(
                    "Test",
                    "request ${
                        ex.response()?.raw()?.request()?.url()
                    } error, code: ${ex.code()}, message: ${ex.message()}"
                )
            } catch (e: Exception) {
                Log.e("Test", "system error: ${e.message}")
            }
        }
    }

    fun handleLike(like: Boolean) {
        Log.e("Test", "handleLike $like")
        _likesNum.value = when (like) {
            true -> _likesNum.value!! + 1
            false -> if (_likesNum.value!! < 0) 0 else _likesNum.value!! - 1
        }
    }
}
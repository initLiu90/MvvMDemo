package com.lzp.mvvmdemo.calc.delegate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lzp.mvvmdemo.Event

class MinusDelegate : ViewModeDelegate() {
    private val _left = MutableLiveData(0)
    val left: LiveData<Int> = _left

    private val _right = MutableLiveData(0)
    val right: LiveData<Int> = _right

    private val _result = MutableLiveData(0)
    val result: LiveData<Int> = _result

    override fun onEvent(event: Event): Boolean =
        when (event) {
            is Event.MinusLeft -> {
                if (_left.value != event.num) {
                    _left.value = event.num
                    _result.value = _left.value!! - _right.value!!
                }
                true
            }
            is Event.MinusRight -> {
                if (_right.value != event.num) {
                    _right.value = event.num
                    _result.value = _left.value!! - _right.value!!
                }
                true
            }
            else -> false
        }
}
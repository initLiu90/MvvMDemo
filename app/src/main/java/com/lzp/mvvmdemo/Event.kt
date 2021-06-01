package com.lzp.mvvmdemo

sealed class Event {
    data class PlushLeft(val num: Int) : Event()
    data class PlushRight(val num: Int) : Event()

    data class MinusLeft(val num: Int) : Event()
    data class MinusRight(val num: Int) : Event()
}
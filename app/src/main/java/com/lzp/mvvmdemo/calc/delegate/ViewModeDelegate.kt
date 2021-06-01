package com.lzp.mvvmdemo.calc.delegate

import com.lzp.mvvmdemo.Event

abstract class ViewModeDelegate {

    abstract fun onEvent(event: Event): Boolean
}
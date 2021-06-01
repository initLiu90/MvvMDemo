package com.lzp.mvvmdemo.calc

import androidx.lifecycle.ViewModel
import com.lzp.mvvmdemo.Event
import com.lzp.mvvmdemo.calc.delegate.ViewModeDelegate
import kotlin.reflect.KClass

class CalcActivityViewModel(delegates: List<ViewModeDelegate>) : ViewModel() {
    private val delegateMap = mutableMapOf<KClass<*>, ViewModeDelegate>()

    init {
        delegates.forEach {
            delegateMap[it::class] = it
        }
    }

    fun <T : ViewModeDelegate> getDelegate(delegate: KClass<T>): T =
        delegateMap[delegate] as T

    fun event(event: Event) {
        delegateMap.any {
            it.value.onEvent(event)
        }
    }
}
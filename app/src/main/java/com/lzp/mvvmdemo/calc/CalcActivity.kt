package com.lzp.mvvmdemo.calc

import android.app.Application
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lzp.mvvmdemo.Event
import com.lzp.mvvmdemo.R
import com.lzp.mvvmdemo.calc.delegate.MinusDelegate
import com.lzp.mvvmdemo.calc.delegate.PlusDelegate
import kotlinx.android.synthetic.main.activity_calc.*
import java.lang.reflect.InvocationTargetException

class CalcActivity : AppCompatActivity() {

    private val viewModel by viewModels<CalcActivityViewModel> {
        CalcViewModelProviderFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)
        initView()
        observeState()
    }

    private fun initView() {
        activity_calc_plus_left_et.doOnTextChanged { text, _, _, _ ->
            text?.apply {
                val num = toString().toIntOrNull() ?: 0
                viewModel.event(Event.PlushLeft(num))
            }
        }

        activity_calc_plus_right_et.doOnTextChanged { text, _, _, _ ->
            text?.apply {
                val num = toString().toIntOrNull() ?: 0
                viewModel.event(Event.PlushRight(num))
            }
        }

        activity_calc_minus_left_et.doOnTextChanged { text, _, _, _ ->
            text?.apply {
                val num = toString().toIntOrNull() ?: 0
                viewModel.event(Event.MinusLeft(num))
            }
        }

        activity_calc_minus_right_et.doOnTextChanged { text, _, _, _ ->
            text?.apply {
                val num = toString().toIntOrNull() ?: 0
                viewModel.event(Event.MinusRight(num))
            }
        }
    }

    private fun observeState() {
        viewModel.getDelegate(PlusDelegate::class).left.observe(this) {
            activity_calc_plus_left_et.text = SpannableStringBuilder("${it}")
        }
        viewModel.getDelegate(PlusDelegate::class).right.observe(this) {
            activity_calc_plus_right_et.text = SpannableStringBuilder("${it}")
        }
        viewModel.getDelegate(PlusDelegate::class).result.observe(this) {
            activity_calc_plus_result_tv.text = "${it}"
        }

        viewModel.getDelegate(MinusDelegate::class).left.observe(this) {
            activity_calc_minus_left_et.text = SpannableStringBuilder("${it}")
        }
        viewModel.getDelegate(MinusDelegate::class).right.observe(this) {
            activity_calc_minus_right_et.text = SpannableStringBuilder("${it}")
        }

        viewModel.getDelegate(MinusDelegate::class).result.observe(this) {
            activity_calc_minus_result_tv.text = "${it}"
        }
    }
}

class CalcViewModelProviderFactory(application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (CalcActivityViewModel::class.java.isAssignableFrom(modelClass)) {
            return try {
                modelClass.getConstructor(List::class.java)
                    .newInstance(
                        listOf(PlusDelegate(), MinusDelegate())
                    )
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InvocationTargetException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        } else {
            super.create(modelClass)
        }
    }
}
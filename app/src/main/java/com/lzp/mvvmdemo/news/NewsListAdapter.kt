package com.lzp.mvvmdemo.news

import com.lzp.mvvmdemo.common.ItemViewModel
import com.lzp.mvvmdemo.common.MultiTypeAdapter
import com.lzp.mvvmdemo.common.ViewBinder

class NewsListAdapter(viewBinders: ViewBinder.ViewBinderProvider) : MultiTypeAdapter(viewBinders) {
    private val data = mutableListOf<ItemViewModel>()

    fun setData(news: List<ItemViewModel>) {
        data.clear()
        data.addAll(news)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun getItem(position: Int): ItemViewModel = data[position]
}

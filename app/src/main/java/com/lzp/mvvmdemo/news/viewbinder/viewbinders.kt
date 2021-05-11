package com.lzp.mvvmdemo.news.viewbinder

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lzp.mvvmdemo.R
import com.lzp.mvvmdemo.common.ViewBinder
import com.lzp.mvvmdemo.common.ViewHolder
import com.lzp.mvvmdemo.news.viewmodels.FooterItemViewMode
import com.lzp.mvvmdemo.news.viewmodels.HeaderItemViewModel
import com.lzp.mvvmdemo.news.viewmodels.NewsItemViewModel
import kotlinx.android.synthetic.main.adapter_item_news.view.*

class HeaderViewBinder : ViewBinder<HeaderItemViewModel>() {
    override val itemViewType: Int
        get() = R.layout.adapter_item_news_header

    override fun createViewHolder(parent: ViewGroup): ViewHolder<HeaderItemViewModel> =
        HeaderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(itemViewType, parent, false)
        )

    class HeaderViewHolder(view: View) : ViewHolder<HeaderItemViewModel>(view) {
        override fun bind(position: Int, data: HeaderItemViewModel) = Unit
    }
}


class FooterViewBinder : ViewBinder<FooterItemViewMode>() {
    override val itemViewType: Int
        get() = R.layout.adapter_item_news_footer

    override fun createViewHolder(parent: ViewGroup): ViewHolder<FooterItemViewMode> =
        FooterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(itemViewType, parent, false)
        )

    class FooterViewHolder(view: View) : ViewHolder<FooterItemViewMode>(view) {
        override fun bind(position: Int, data: FooterItemViewMode) = Unit
    }
}

class NewsItemViewBinder(private val lifecycleOwner: LifecycleOwner) :
    ViewBinder<NewsItemViewModel>() {
    override val itemViewType: Int
        get() = R.layout.adapter_item_news

    override fun createViewHolder(parent: ViewGroup): ViewHolder<NewsItemViewModel> =
        NewsItemViewHolder(
            lifecycleOwner,
            LayoutInflater.from(parent.context).inflate(itemViewType, parent, false)
        )

    class NewsItemViewHolder(private val lifecycleOwner: LifecycleOwner, view: View) :
        ViewHolder<NewsItemViewModel>(view) {

        private val titleTv = view.adapter_item_news_title_tv
        private val likeTv = view.adapter_item_news_like_tv
        private val coverIv = view.adapter_item_news_cover_iv
        private val loadingPb = view.adapter_item_news_loading_pb

        override fun bind(position: Int, data: NewsItemViewModel) {
            titleTv.text = data.title

            data.like.observe(lifecycleOwner) {
                likeTv.text = it
            }
            likeTv.setOnClickListener { data.handleLike() }


            data.showLoading.observe(lifecycleOwner) {
                loadingPb.visibility = when (it) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
            }

            Glide.with(coverIv)
                .load(data.coverUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean = false

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        data.onCoverReady()
                        return false
                    }
                })
                .into(coverIv)
        }
    }
}
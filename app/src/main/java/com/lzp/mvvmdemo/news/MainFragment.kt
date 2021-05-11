package com.lzp.mvvmdemo.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzp.mvvmdemo.R
import com.lzp.mvvmdemo.common.ItemViewModel
import com.lzp.mvvmdemo.common.ViewBinder
import com.lzp.mvvmdemo.news.viewbinder.FooterViewBinder
import com.lzp.mvvmdemo.news.viewbinder.HeaderViewBinder
import com.lzp.mvvmdemo.news.viewbinder.NewsItemViewBinder
import com.lzp.mvvmdemo.news.viewmodels.FooterItemViewMode
import com.lzp.mvvmdemo.news.viewmodels.HeaderItemViewModel
import com.lzp.mvvmdemo.news.viewmodels.NewsItemViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {
    companion object {
        const val TAG = "MainFragment"

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainFragmentViewModel by viewModels()
    private lateinit var adapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView(view)
        observerData()
        viewModel.fetchNews()
    }

    private fun setupRecyclerView(root: View) {
        root.fragment_main_news_rv.apply {
            this@MainFragment.adapter = NewsListAdapter(createViewBinders())
            adapter = this@MainFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun createViewBinders(): ViewBinder.ViewBinderProvider =
        ViewBinder.ViewBinderProvider()
            .addViewBinder(HeaderItemViewModel::class, HeaderViewBinder())
            .addViewBinder(FooterItemViewMode::class, FooterViewBinder())
            .addViewBinder(NewsItemViewModel::class, NewsItemViewBinder(viewLifecycleOwner))

    private fun observerData() {
        viewModel.news.observe(viewLifecycleOwner, { stories ->
            val itemViewModels = mutableListOf<ItemViewModel>(HeaderItemViewModel())
            itemViewModels.addAll(stories.map { NewsItemViewModel(it) })
            itemViewModels.add(FooterItemViewMode())

            adapter.setData(itemViewModels)
        })
    }
}
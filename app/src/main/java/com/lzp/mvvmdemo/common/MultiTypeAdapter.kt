package com.lzp.mvvmdemo.common

import android.view.View
import android.view.ViewGroup
import androidx.collection.arrayMapOf
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

abstract class MultiTypeAdapter(protected val viewBinderProvider: ViewBinder.ViewBinderProvider) :
    RecyclerView.Adapter<ViewHolder<ItemViewModel>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemViewModel> =
        viewBinderProvider.getViewBinder(viewType).createViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder<ItemViewModel>, position: Int) {
        holder.bind(position, getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItemViewTypeInner(getItem(position))
    }

    private inline fun <T : Any> getItemViewTypeInner(data: T): Int =
        viewBinderProvider.getViewBinder(data::class).itemViewType

    abstract fun getItem(position: Int): ItemViewModel
}

abstract class ViewHolder<Data : ItemViewModel>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(position: Int, data: Data)
}

abstract class ViewBinder<T : ItemViewModel> {
    abstract val itemViewType: Int
    abstract fun createViewHolder(parent: ViewGroup): ViewHolder<T>

    class ViewBinderProvider {
        private val viewBinders = arrayMapOf<KClass<*>, ViewBinder<*>>()
        private val itemViewTypes = arrayMapOf<Int, ViewBinder<*>>()

        fun addViewBinder(
            type: KClass<*>,
            viewBinder: ViewBinder<out ItemViewModel>
        ): ViewBinderProvider {
            viewBinders[type] = viewBinder
            itemViewTypes[viewBinder.itemViewType] = viewBinder
            return this
        }


        fun getViewBinder(type: KClass<*>): ViewBinder<ItemViewModel> =
            viewBinders[type] as? ViewBinder<ItemViewModel>
                ?: throw IllegalArgumentException("UnSupport data type ${type.simpleName}")

        fun getViewBinder(itemViewType: Int): ViewBinder<ItemViewModel> =
            itemViewTypes[itemViewType] as? ViewBinder<ItemViewModel>
                ?: throw IllegalArgumentException("UnSupport item view type $itemViewType")
    }
}
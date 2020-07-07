package com.nik.pokemon.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.pokemon.utils.Const.PRELOAD_FROM_SERVER_ITEMS_COUNT

class RecyclerViewAdapter<T, S>(
    val clickListener: S? = null,
    val layout: Int,
    val onScrollLastPageListener: (() -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data: MutableList<T> = ArrayList()
    var isAllItemLoaded: Boolean = false

    fun update(items: List<T>) {
        this.data.clear()
        this.data.addAll(items)
    }

    fun addItems(items: List<T>) {
        this.data.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderFactory.create(inflater, parent, viewType)
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) = layout

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(!isAllItemLoaded && position >= data.size - PRELOAD_FROM_SERVER_ITEMS_COUNT) {
            onScrollLastPageListener?.invoke()
        }
        (holder as? Binder)?.bind (
            data = data[position],
            listener = clickListener
        )
    }




}
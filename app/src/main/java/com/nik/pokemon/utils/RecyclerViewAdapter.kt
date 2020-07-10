package com.nik.pokemon.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.utils.Const.PRELOAD_FROM_SERVER_ITEMS_COUNT

class RecyclerViewAdapter<T, S>(
    val clickListener: S? = null,
    val layout: Int,
    comparator: DiffUtil.ItemCallback<T>
) : ListAdapter<T, RecyclerView.ViewHolder>(comparator) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? Binder)?.bind(
            data = getItem(position),
            listener = clickListener
        )
    }

    override fun getItemViewType(position: Int): Int = layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderFactory.create(inflater, parent, viewType)
    }

    companion object {

        val COMPARATOR = object : DiffUtil.ItemCallback<PokemonView>() {
            override fun areItemsTheSame(oldItem: PokemonView, newItem: PokemonView): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokemonView, newItem: PokemonView): Boolean =
                oldItem == newItem
        }
    }
}
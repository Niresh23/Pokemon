package com.nik.pokemon.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.pokemon.R
import com.nik.pokemon.databinding.ItemCardviewBinding
import com.nik.pokemon.model.PokemonView

object ViewHolderFactory {
    fun create(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) : RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.item_cardview -> ItemCardHolder(
                ItemCardviewBinding.inflate(inflater, parent, false)
            )

            else -> throw Exception("Wrong view type")
        }
    }

    class ItemCardHolder(private val binding: ItemCardviewBinding) :
        RecyclerView.ViewHolder(binding.root), Binder {
        override fun <T, S> bind(data: T, position: Int, listener: S?) {
            binding.listener = listener as? OnPokemonClickListener
            binding.pokemonView = data as? PokemonView
            binding.executePendingBindings()
        }

    }

}
package com.nik.pokemon.utils

import com.nik.pokemon.model.PokemonView

interface OnPokemonClickListener {
    fun onClick(pokemon: PokemonView)
}
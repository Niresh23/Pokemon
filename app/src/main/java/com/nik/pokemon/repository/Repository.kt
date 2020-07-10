package com.nik.pokemon.repository

import com.nik.pokemon.model.PokemonView
import io.reactivex.Single

interface Repository {
    fun execute(limit: Int, offset: Int): Single<List<PokemonView>>
}
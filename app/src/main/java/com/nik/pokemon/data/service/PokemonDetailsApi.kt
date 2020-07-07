package com.nik.pokemon.data.service

import com.nik.pokemon.data.model.PokemonDetailsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonDetailsApi {
    @GET
    fun getPokemonDetails(@Url url: String): Single<PokemonDetailsResponse>
}
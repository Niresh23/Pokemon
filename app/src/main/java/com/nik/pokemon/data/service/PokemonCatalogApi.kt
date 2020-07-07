package com.nik.pokemon.data.service

import com.nik.pokemon.data.model.PokemonCatalogResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonCatalogApi {
    @GET("?")
    fun getPokemonCatalog(
    @Query("limit") limit: Int,
    @Query("offset") offset: Int
    ): Single<PokemonCatalogResponse>
}
package com.nik.pokemon.data.model

data class PokemonCatalogResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>
) {
    data class Result(
        val name: String,
        val url: String
    )
}

fun PokemonCatalogResponse.toUrlsStringList(): List<String> = this.results.map { it.url }
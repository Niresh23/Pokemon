package com.nik.pokemon.data.model

import com.google.gson.annotations.SerializedName
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.utils.Utils

data class PokemonDetailsResponse(
    val name: String,
    val id: Int,
    val abilities: List<AbilityInformation>,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val types: List<TypeInformation>,
    val stats: List<StatInformation>,
    val sprites: Sprite
) {
    data class AbilityInformation(
        val ability: Ability,
        @SerializedName("is_hidden")
        val isHidden: Boolean,
        val slot: Int
    ) {
        data class Ability(
            val name: String,
            val url: String
        )
    }

    data class TypeInformation(
        val slot: Int,
        val type: Type
    ) {
        data class Type(
            val name: String,
            val url: String
        )
    }

    data class StatInformation(
        @SerializedName("base_stat")
        val baseStat: Int,
        val effort: Int,
        val stat: Stat
    ) {
        data class Stat(
            val name: String,
            val url: String
        )
    }

    data class Sprite(
        @SerializedName("back_default")
        val backDefaultUrl: String?,
        @SerializedName("front_default")
        val frontDefaultUrl: String?

    )
}

fun PokemonDetailsResponse.toPokemonView() = PokemonView(
    name = this.name,
    poster = Utils.getImageFromUrl(this.sprites.frontDefaultUrl),
    id = this.id,
    height = this.height,
    weight = this.weight,
    type = this.types.joinToString { it.type.name },
    attack = this.stats.filter { information ->
        information.stat.name.equals("attack", true) }[0].baseStat,
    defence = this.stats.filter { information ->
        information.stat.name.equals("defense", true) }[0].baseStat,
    hp = this.stats.filter { information ->
        information.stat.name.equals("hp", true) }[0].baseStat
)

package com.nik.pokemon.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.utils.Utils

@Entity
data class PokemonEntity (
    @PrimaryKey
    val id: Int,
    @ColumnInfo
    val name: String,
    val poster: String?,

    @ColumnInfo
    val height: Int,
    @ColumnInfo
    val weight: Int,
    @ColumnInfo
    val type: String,
    @ColumnInfo
    val attack: Int,
    @ColumnInfo
    val defence: Int,
    @ColumnInfo
    val hp: Int
)

fun PokemonEntity.toPokemonView() = PokemonView(
    id = this.id,
    name = this.name,
    poster = Utils.decode(this.poster),
    height = this.height,
    weight = this.weight,
    type = this.type,
    attack = this.attack,
    defence = this.defence,
    hp = this.hp
)

fun PokemonView.toPokemonEntity() = PokemonEntity(
    id = this.id,
    name = this.name,
    poster = Utils.encode(this.poster),
    height = this.height,
    weight = this.weight,
    type = this.type,
    attack = this.attack,
    defence = this.defence,
    hp = this.hp
)
package com.nik.pokemon.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nik.pokemon.model.PokemonView

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

fun PokemonView.toPokemonEntity() = PokemonEntity(
    id = this.id,
    name = this.name,
    poster = this.
)
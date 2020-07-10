package com.nik.pokemon.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonView (
    val name: String,
    val poster: Bitmap,
    val id: Int,
    val height: Int,
    val weight: Int,
    val type: String,
    val attack: Int,
    val defence: Int,
    val hp: Int
) : Parcelable
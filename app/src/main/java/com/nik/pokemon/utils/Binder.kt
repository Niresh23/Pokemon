package com.nik.pokemon.utils

interface Binder {
    fun <T, S> bind(data: T, position: Int = 0, listener: S?)
}
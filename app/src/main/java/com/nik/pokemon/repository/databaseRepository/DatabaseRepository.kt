package com.nik.pokemon.repository.databaseRepository

import com.nik.pokemon.data.database.PokemonDao
import com.nik.pokemon.data.database.toPokemonEntity
import com.nik.pokemon.data.database.toPokemonView
import com.nik.pokemon.model.PokemonView
import io.reactivex.Completable
import io.reactivex.Single

class DatabaseRepository(private val dao: PokemonDao) {

    fun getAllPokemon(): Single<List<PokemonView>> = dao
        .getList().map { result ->
        result.map{ it.toPokemonView()}
    }

    fun insertPokemon(list: List<PokemonView>): Completable = dao
        .insert(
        list.map { it.toPokemonEntity() }
    )

    fun deleteAll(): Completable = dao.deleteAll()

    fun getPokemonOffset(limit: Int, offset: Int): Single<List<PokemonView>> = dao
        .getPokemonOffset(limit, offset).map { result
            -> result.map { it.toPokemonView() } }

}
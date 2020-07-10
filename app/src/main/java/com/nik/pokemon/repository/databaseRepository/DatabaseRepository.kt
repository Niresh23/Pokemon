package com.nik.pokemon.repository.databaseRepository

import com.nik.pokemon.data.database.PokemonDao
import com.nik.pokemon.data.database.PokemonEntity
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.repository.Repository
import io.reactivex.Completable
import io.reactivex.Single

class DatabaseRepository(private val dao: PokemonDao): Repository {
    override fun execute(limit: Int, offset: Int): Single<List<PokemonView>> {

    }

    fun insertData(list: List<PokemonView>): Completable =
        dao.insert(list.map{})


}
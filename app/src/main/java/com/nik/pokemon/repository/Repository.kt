package com.nik.pokemon.repository

import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.repository.databaseRepository.DatabaseRepository
import com.nik.pokemon.repository.remoteRepository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.Single

class Repository(private val remoteRepository: RemoteRepository,
private val databaseRepository: DatabaseRepository) {

    fun getFromRemote(limit: Int, offset: Int): Single<List<PokemonView>> =
        remoteRepository.execute(limit, offset)

    fun getFromDatabase(): Single<List<PokemonView>> =
        databaseRepository.getAllPokemon()

    fun updateDatabase(list: List<PokemonView>): Completable =
        databaseRepository.insertPokemon(list)

    fun clearDatabase(): Completable =
        databaseRepository.deleteAll()
}
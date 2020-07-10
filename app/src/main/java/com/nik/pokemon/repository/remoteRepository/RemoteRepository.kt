package com.nik.pokemon.repository.remoteRepository

import android.util.Log
import com.nik.pokemon.data.model.PokemonCatalogResponse
import com.nik.pokemon.data.model.PokemonDetailsResponse
import com.nik.pokemon.data.model.toPokemonView
import com.nik.pokemon.data.model.toUrlsStringList
import com.nik.pokemon.data.service.PokemonCatalogApi
import com.nik.pokemon.data.service.PokemonDetailsApi
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.repository.Repository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers.io

class RemoteRepository(private val pokemonCatalogApi: PokemonCatalogApi,
                       private val pokemonDetailsApi: PokemonDetailsApi) {
    fun execute(limit: Int, offset: Int): Single<List<PokemonView>> =

        pokemonCatalogApi.getPokemonCatalog(limit, offset)
            .flatMapObservable { response: PokemonCatalogResponse ->
                Log.d("my_tag", response.toUrlsStringList().toString())
                Observable.fromIterable(response.toUrlsStringList())
            }
            .flatMapSingle { item: String ->
                pokemonDetailsApi.getPokemonDetails(item).map { it.toPokemonView() }
            }.toList()
}
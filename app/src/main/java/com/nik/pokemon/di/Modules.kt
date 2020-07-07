package com.nik.pokemon.di

import com.nik.pokemon.utils.Const
import com.nik.pokemon.data.service.PokemonCatalogApi
import com.nik.pokemon.data.service.PokemonDetailsApi
import com.nik.pokemon.repository.remoteRepository.RemoteRepository
import com.nik.pokemon.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private fun getPokemonCatalogApi(retrofit: Retrofit) =
    retrofit.create(PokemonCatalogApi::class.java)

private fun getPokemonDetailsApi(retrofit: Retrofit) =
    retrofit.create(PokemonDetailsApi::class.java)

val remoteModule = module {

    single<Retrofit> { Retrofit.Builder().baseUrl(Const.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build() }

    single { RemoteRepository(get(), get()) }

    single<PokemonCatalogApi> { getPokemonCatalogApi(get()) }
    single<PokemonDetailsApi> { getPokemonDetailsApi(get()) }


}

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}



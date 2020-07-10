package com.nik.pokemon.di

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import androidx.room.Room
import com.nik.pokemon.data.database.PokemonDao
import com.nik.pokemon.data.database.PokemonDatabase
import com.nik.pokemon.utils.Const
import com.nik.pokemon.data.service.PokemonCatalogApi
import com.nik.pokemon.data.service.PokemonDetailsApi
import com.nik.pokemon.repository.Repository
import com.nik.pokemon.repository.databaseRepository.DatabaseRepository
import com.nik.pokemon.repository.remoteRepository.RemoteRepository
import com.nik.pokemon.ui.main.MainViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.Exception


private fun getPokemonCatalogApi(retrofit: Retrofit) =
    retrofit.create(PokemonCatalogApi::class.java)

private fun getPokemonDetailsApi(retrofit: Retrofit) =
    retrofit.create(PokemonDetailsApi::class.java)

private fun getDatabaseInstance(context: Context) =
    Room.databaseBuilder(context, PokemonDatabase::class.java, Const.DATABASE_NAME).build()

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
val repositoryModule = module {
    single<Repository> { Repository(get(), get())}
}
val databaseModule = module {
    single<PokemonDatabase> { getDatabaseInstance(androidContext())}
    single<PokemonDao> { getDatabaseInstance(get()).pokemonDao()}
    single<DatabaseRepository> { DatabaseRepository(get()) }
}



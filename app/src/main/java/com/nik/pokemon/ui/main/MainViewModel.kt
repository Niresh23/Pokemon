package com.nik.pokemon.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.repository.remoteRepository.RemoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers.io

class MainViewModel(private val remoteRepository: RemoteRepository) : ViewModel() {
    val liveData = MutableLiveData<List<PokemonView>>()
    val errorLiveData = MutableLiveData<Throwable>()

    fun getData() {
        remoteRepository.execute(30, 60).subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : DisposableSingleObserver<List<PokemonView>>(){
                override fun onSuccess(list: List<PokemonView>) {
                    liveData.value = list
                }

                override fun onError(e: Throwable) {
                    errorLiveData.value = e
                    Log.e("my_tag", e.message ?: "NO DATA")
                    e.printStackTrace()
                }
            })
    }
}
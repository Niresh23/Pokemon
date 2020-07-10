package com.nik.pokemon.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nik.pokemon.BaseViewModel
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.repository.remoteRepository.RemoteRepository
import com.nik.pokemon.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers.io

class MainViewModel(private val remoteRepository: RemoteRepository) : BaseViewModel() {

    companion object {
        const val LIMIT = 30
        const val OFFSET = 30
        const val ADD_OFFSET = 30
    }

    var currentOffset = OFFSET

    val liveData = MutableLiveData<List<PokemonView>>()
    val errorLiveData = MutableLiveData<Throwable>()

    fun getData() {
        remoteRepository.execute(LIMIT, OFFSET).subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(::handleOnSuccessGetData, ::handleFailure)
            .addTo(compositeDisposable)
    }

    fun getMoreData() {
        currentOffset += ADD_OFFSET
        remoteRepository.execute(LIMIT, currentOffset).subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(::handleOnSuccessGetMoreData, ::handleFailure)
            .addTo(compositeDisposable)
    }

    private fun handleOnSuccessGetData(result: List<PokemonView>) {
        liveData.value = result
    }

    private fun handleOnSuccessGetMoreData(result: List<PokemonView>) {
        liveData.value = liveData.value?.toMutableList().apply {
            this?.addAll(result)
        }
    }

}
package com.nik.pokemon.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nik.pokemon.BaseViewModel
import com.nik.pokemon.di.databaseModule
import com.nik.pokemon.model.PokemonView
import com.nik.pokemon.repository.Repository
import com.nik.pokemon.repository.remoteRepository.RemoteRepository
import com.nik.pokemon.utils.LoadState
import com.nik.pokemon.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers.io
import kotlin.random.Random

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    companion object {
        const val LIMIT = 30
        const val OFFSET = 0
        const val ADD_OFFSET = 30
    }
    private var isLoadingListInProgress = false

    var currentOffset = OFFSET

    val liveData = MutableLiveData<List<PokemonView>>()
    var loadingState = MutableLiveData<LoadState>()
    val callbackLiveData = MutableLiveData<String>()

    fun getRandom(isConnected: Boolean) {
        if (isConnected) {
            repository.getFromRemote(LIMIT, (0..900).random()).subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleOnSuccessGetData, ::handleFailureLoadData)
                .addTo(compositeDisposable)
        } else {
            repository.getFromDatabase(LIMIT, (0..900).random()).subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleOnSuccessGetData, ::handleFailureLoadData)
                .addTo(compositeDisposable)
        }
    }

    fun getData(isConnected: Boolean) {
        if (isConnected) {
            repository.getFromRemote(LIMIT, OFFSET).subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleOnSuccessGetData, ::handleFailureLoadData)
                .addTo(compositeDisposable)
        } else {
            repository.getFromDatabase(LIMIT, OFFSET).subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::handleOnSuccessGetData, ::handleFailureLoadData)
                .addTo(compositeDisposable)
        }
    }

    fun getMoreData(isConnected: Boolean) {
        if(isLoadingListInProgress) return
        isLoadingListInProgress = true
        loadingState.postValue(LoadState.Loading)
        currentOffset += ADD_OFFSET
        if(isConnected) {
            repository.getFromRemote(LIMIT, currentOffset).subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(::handleOnSuccessGetMoreData, ::handleFailureLoadData)
            .addTo(compositeDisposable)
        } else {
            repository.getFromDatabase(LIMIT, currentOffset).subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(::handleOnSuccessGetMoreData, ::handleFailureLoadData)
                .addTo(compositeDisposable)
        }

    }

    private fun handleOnSuccessGetData(result: List<PokemonView>) {
        liveData.value = result
        repository.updateDatabase(result).subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(::handleOnComplete, ::handleFailureLoadData).addTo(compositeDisposable)
    }

    private fun handleOnComplete() {
        callbackLiveData.value = "Database up to date"
    }
    private fun handleOnSuccessGetMoreData(result: List<PokemonView>) {
        loadingState.postValue(LoadState.Done)
        liveData.value = liveData.value?.toMutableList().apply {
            this?.addAll(result)
        }

        isLoadingListInProgress = false
        repository.updateDatabase(result).subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(::handleOnComplete, ::handleFailureLoadData).addTo(compositeDisposable)
    }

    private fun handleFailureLoadData(failure: Throwable) {
        loadingState.postValue(LoadState.Error(failure))
        currentOffset -= ADD_OFFSET
        isLoadingListInProgress = false
    }

    fun sortArrayBy(attack: Boolean = false, defence: Boolean = false, hp: Boolean = false) {
        Log.d("my_log", attack.toString() + defence.toString() + hp.toString())
        if (attack && !defence && !hp) {
            liveData.value = liveData.value?.toMutableList()?.sortedWith(compareBy <PokemonView>{ it.attack })?.reversed()
        } else if (attack && defence && !hp) {
            liveData.value = liveData.value?.toMutableList()?.sortedWith(compareBy <PokemonView>{ it.attack }.thenBy{it.defence})?.reversed()
        } else if(attack && !defence && hp) {
            liveData.value = liveData.value?.toMutableList()?.sortedWith(compareBy <PokemonView>{it.attack}.thenBy { it.hp })?.reversed()
        } else if (attack && defence && hp) {
            liveData.value = liveData.value?.toMutableList()?.sortedWith(compareBy <PokemonView>{ it.attack }.thenBy{it.defence}.thenBy { it.hp })?.reversed()
        } else if (!attack && defence && !hp) {
            liveData.value = liveData.value?.toMutableList()?.sortedWith(compareBy <PokemonView>{it.defence})?.reversed()
        } else if (!attack && defence && hp) {
            liveData.value = liveData.value?.toMutableList()?.sortedWith(compareBy <PokemonView>{it.defence}.thenBy {it.hp})?.reversed()
        } else if (!attack && !defence && hp) {
            liveData.value = liveData.value?.toMutableList()?.sortedWith(compareBy <PokemonView>{it.hp})?.reversed()
        }
    }

}
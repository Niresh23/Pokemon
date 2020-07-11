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

}
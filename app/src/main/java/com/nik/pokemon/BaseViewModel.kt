package com.nik.pokemon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {
    protected val compositeDisposable by lazy{ CompositeDisposable() }

    var failure: MutableLiveData<Throwable> = MutableLiveData()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected open fun handleFailure(failure: Throwable) {
        this.failure.value = failure
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
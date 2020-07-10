package com.nik.pokemon.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun RecyclerView.addOnScrollListenerPagination(
    layoutManager: LinearLayoutManager,
    callBack: () -> Unit
) {
    clearOnScrollListeners()

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

            if (lastVisibleItem + Const.PRELOAD_FROM_SERVER_ITEMS_COUNT == totalItemCount) {
                callBack.invoke()
            }
        }
    })
}
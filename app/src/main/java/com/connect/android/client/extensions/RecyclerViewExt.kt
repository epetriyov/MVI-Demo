package com.connect.android.client.extensions

import androidx.annotation.CheckResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Create an observable of scroll events on `recyclerView`.
 *
 * *Warning:* The created observable keeps a strong reference to `recyclerView`.
 * Unsubscribe to free this reference.
 */
@CheckResult
fun RecyclerView.scrollsToEnd(layoutManager: LinearLayoutManager): Observable<Int> =
    RecyclerViewScrollToEndEventObservable(this, layoutManager)

private class RecyclerViewScrollToEndEventObservable(
        private val view: RecyclerView,
        private val layoutManager: LinearLayoutManager
) : Observable<Int>() {

    override fun subscribeActual(observer: Observer<in Int>) {
        val listener = Listener(
            view, layoutManager, observer
        )
        observer.onSubscribe(listener)
        view.addOnScrollListener(listener.scrollListener)
    }

    class Listener(
            private val recyclerView: RecyclerView,
            private val layoutManager: LinearLayoutManager,
            observer: Observer<in Int>
    ) : MainThreadDisposable() {

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!isDisposed && dy != 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                        observer.onNext(totalItemCount)
                    }
                }
            }
        }

        override fun onDispose() {
            recyclerView.removeOnScrollListener(scrollListener)
        }
    }
}
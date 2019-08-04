package com.connect.android.client.modules.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun itemClicks(): Observable<Int> {
        return containerView.clicks().map { adapterPosition }.publish().autoConnect()
    }
}
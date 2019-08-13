package com.connect.android.client.modules.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.connect.android.client.R
import com.connect.android.client.model.events.Event
import com.connect.android.client.modules.base.BaseViewHolder
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.adt_event.*

class EventsAdapter(
    private val layoutInflater: LayoutInflater
) :
    ListAdapter<Event, EventViewHolder>(
        object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    private val selectionsSubject = PublishSubject.create<Event>()

    private val participantsSubject = PublishSubject.create<Event>()

    private val actionSubject = PublishSubject.create<Event>()

    fun itemSelections(): Observable<Event> = selectionsSubject

    fun eventParticipants(): Observable<Event> = participantsSubject

    fun actions(): Observable<Event> = actionSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder(layoutInflater.inflate(R.layout.adt_event, parent, false))
            .apply {
                itemClicks().map { getItem(it) }.subscribeWith(selectionsSubject)
                participants().map { getItem(it) }.subscribeWith(participantsSubject)
                actions().map { getItem(it) }.subscribeWith(actionSubject)
            }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

class EventViewHolder(containerView: View) : BaseViewHolder(containerView) {

    fun participants(): Observable<Int> = btn_participants.clicks().map { adapterPosition }

    fun actions(): Observable<Int> = btn_apply.clicks().map { adapterPosition }

    fun bindView(item: Event) {
        EventBinder.bindEvent(itemView, item)
        btn_apply.isVisible = !item.accept
    }

}
package com.connect.android.client.modules.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.request.RequestOptions
import com.connect.android.client.R
import com.connect.android.client.model.profile.User
import com.connect.android.client.modules.base.BaseViewHolder
import com.connect.android.client.modules.common.ProfileBinder.buildChip
import com.connect.android.client.tools.glide.GlideApp
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.adt_contact.*

class ContactsAdapter(
    private val layoutInflater: LayoutInflater
) :
    ListAdapter<User, ContactsViewHolder>(
        object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    private val selectionsSubject = PublishSubject.create<User>()

    private val chatSubject = PublishSubject.create<User>()

    fun itemSelections(): Observable<User> = selectionsSubject

    fun chats(): Observable<User> = chatSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactsViewHolder(layoutInflater.inflate(R.layout.adt_contact, parent, false))
            .apply {
                itemClicks().map { getItem(it) }.subscribeWith(selectionsSubject)
                actions().map { getItem(it) }.subscribeWith(chatSubject)
            }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

class ContactsViewHolder(view: View) : BaseViewHolder(view) {

    fun actions(): Observable<Int> = btn_message.clicks().map { adapterPosition }

    fun bindView(item: User) {
        GlideApp.with(containerView)
            .load(item.avatar)
            .error(R.drawable.ic_placeholder)
            .apply(RequestOptions.circleCropTransform())
            .into(img_avatar)
        label_name.text = item.name
        label_work.text = item.getWorkInfo()
        label_location.text =
            String.format(containerView.context.getString(R.string.distance), item.distance)
        value_skills.text = item.about
        with(chips_skills) {
            removeAllViews()
            item.fields?.forEach { addView(buildChip(context, it)) }
        }
    }

}
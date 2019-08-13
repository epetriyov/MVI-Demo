package com.connect.android.client.modules.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.request.RequestOptions
import com.connect.android.client.R
import com.connect.android.client.model.chats.Chat
import com.connect.android.client.modules.base.BaseViewHolder
import com.connect.android.client.tools.glide.GlideApp
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.adt_chat.*
import org.joda.time.format.DateTimeFormat

class ChatsAdapter(
    private val layoutInflater: LayoutInflater
) :
    ListAdapter<Chat, ChatsViewHolder>(
        object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    private val selectionsSubject = PublishSubject.create<Chat>()

    fun itemSelections(): Observable<Chat> = selectionsSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChatsViewHolder(layoutInflater.inflate(R.layout.adt_chat, parent, false))
            .apply {
                itemClicks().map { getItem(it) }.subscribeWith(selectionsSubject)
            }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

class ChatsViewHolder(view: View) : BaseViewHolder(view) {

    companion object {
        private val timeFormat = DateTimeFormat.forPattern("HH:mm")
    }

    fun bindView(item: Chat) {
        GlideApp.with(containerView)
            .load(item.user.avatar)
            .error(R.drawable.ic_placeholder)
            .apply(RequestOptions.circleCropTransform())
            .into(img_avatar)
        label_name.text = item.user.name
        label_time.text = timeFormat.print(item.lastActiveDate)
        label_last_message.text = item.lastMessage?.text
    }

}
package com.connect.android.client.modules.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.connect.android.client.R
import com.connect.android.client.modules.base.BaseViewHolder
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class MessagesAdapter(private val layoutInflater: LayoutInflater) :
    ListAdapter<DisplayableMessage, MessageViewHolder>(
        object : DiffUtil.ItemCallback<DisplayableMessage>() {
            override fun areItemsTheSame(oldItem: DisplayableMessage, newItem: DisplayableMessage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DisplayableMessage, newItem: DisplayableMessage): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    companion object {
        private const val MY_MESSAGE = 0
        private const val OTHER_MESSAGE = 1
    }

    private val timeFormat = DateTimeFormat.forPattern("HH:mm")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = when (viewType) {
            MY_MESSAGE -> R.layout.adt_my_message
            OTHER_MESSAGE -> R.layout.adt_others_message
            else -> throw IllegalStateException("wrong viewType: $viewType")
        }
        return MessageViewHolder(layoutInflater.inflate(layoutId, parent, false), timeFormat)
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {
        viewHolder.bindView(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isMine) MY_MESSAGE else OTHER_MESSAGE
    }
}

class MessageViewHolder(itemView: View, private val timeFormatter: DateTimeFormatter) : BaseViewHolder(itemView) {

    fun bindView(item: DisplayableMessage) {
        itemView.findViewById<TextView>(R.id.label_message).text = item.text
        itemView.findViewById<TextView>(R.id.label_date).text = timeFormatter.print(item.time.toLocalTime())
    }
}
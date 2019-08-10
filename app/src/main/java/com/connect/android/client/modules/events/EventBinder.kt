package com.connect.android.client.modules.events

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.connect.android.client.R
import com.connect.android.client.model.events.Event
import com.connect.android.client.tools.glide.GlideApp
import org.joda.time.format.DateTimeFormat

object EventBinder {

    private val dayFormat = DateTimeFormat.forPattern("ddMMMMyyyy")
    private val timeFormat = DateTimeFormat.forPattern("HH:mm")

    fun bindEvent(view: View, item: Event) {
        GlideApp.with(view)
            .load(item.picture)
            .centerCrop()
            .error(R.drawable.ic_placeholder)
            .into(view.findViewById(R.id.img_event))
        view.findViewById<TextView>(R.id.label_event_name).text = item.name
        view.findViewById<TextView>(R.id.label_event_description).text = item.description
        view.findViewById<View>(R.id.view_address).findViewById<TextView>(R.id.label_place).text = item.place
        view.findViewById<View>(R.id.view_address).findViewById<TextView>(R.id.label_top).text = item.address
        view.findViewById<View>(R.id.view_address).findViewById<ImageView>(R.id.left_icon)
            .setImageResource(R.drawable.ic_pin_blue)
        view.findViewById<View>(R.id.view_time).findViewById<TextView>(R.id.label_place).text =
            dayFormat.print(item.startDate)
        view.findViewById<View>(R.id.view_time).findViewById<TextView>(R.id.label_top).text = view.resources.getString(
            R.string.dates_format,
            timeFormat.print(item.startDate),
            timeFormat.print(item.finishDate)
        )
        view.findViewById<View>(R.id.view_address).findViewById<ImageView>(R.id.left_icon)
            .setImageResource(R.drawable.ic_clock_blue)
        view.findViewById<TextView>(R.id.btn_participants).text = item.accepted.toString()
    }
}
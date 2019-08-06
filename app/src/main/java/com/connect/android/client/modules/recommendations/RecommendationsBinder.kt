package com.connect.android.client.modules.recommendations

import android.view.View
import android.widget.TextView
import com.connect.android.client.R
import com.connect.android.client.model.profile.User
import com.connect.android.client.tools.glide.GlideApp

object RecommendationsBinder {
    fun bindRecommendation(containerView: View, item: User) {
        containerView.findViewById<TextView>(R.id.label_name).text = item.name
        GlideApp.with(containerView)
            .load(item.avatar)
            .centerCrop()
            .error(R.drawable.ic_placeholder)
            .into(containerView.findViewById(R.id.img_avatar))
        containerView.findViewById<TextView>(R.id.value_skills).text = item.skills?.joinToString()
        containerView.findViewById<TextView>(R.id.value_goals).text = item.goals?.joinToString()
        containerView.findViewById<TextView>(R.id.label_location).text =
            String.format(containerView.context.getString(R.string.distance), item.distance)
        containerView.findViewById<TextView>(R.id.label_work).text = item.getWorkInfo()
    }
}
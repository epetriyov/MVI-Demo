package com.connect.android.client.modules.common

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.request.RequestOptions
import com.connect.android.client.R
import com.connect.android.client.extensions.dp
import com.connect.android.client.model.common.ProfileItem
import com.connect.android.client.model.profile.User
import com.connect.android.client.tools.glide.GlideApp
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

object ProfileBinder {
    fun bindProfileItem(containerView: View, item: ProfileItem) {
        containerView.findViewById<TextView>(R.id.item_header).text = item.getFirstText()
        containerView.findViewById<TextView>(R.id.label_subtitle).text = item.getSecondText()
        containerView.findViewById<TextView>(R.id.label_date).text = item.getDates()
    }

    fun bindProfile(
        containerView: View,
        user: User,
        worksAdapter: ListAdapter<ProfileItem, *>,
        educationsAdapter: ListAdapter<ProfileItem, *>
    ) {
        GlideApp.with(containerView)
            .load(user.avatar)
            .error(R.drawable.ic_placeholder)
            .apply(RequestOptions.circleCropTransform())
            .into(containerView.findViewById(R.id.img_avatar))
        containerView.findViewById<TextView>(R.id.label_name).text = user.name
        containerView.findViewById<TextView>(R.id.value_skills).text = user.about
        containerView.findViewById<TextView>(R.id.value_goals).text = user.goals?.joinToString()
        with(containerView.findViewById<View>(R.id.aims_block).findViewById<ChipGroup>(R.id.chips_block)) {
            removeAllViews()
            user.skills?.forEach { addView(buildChip(context, it)) }
        }
        with(containerView.findViewById<View>(R.id.skills_block).findViewById<ChipGroup>(R.id.chips_block)) {
            removeAllViews()
            user.fields?.forEach { addView(buildChip(context, it)) }
        }
        worksAdapter.submitList(user.works)
        educationsAdapter.submitList(user.educations)
    }

    fun buildChip(context: Context, it: String): View? {
        return Chip(context).apply {
            text = it
            chipBackgroundColor = resources.getColorStateList(R.color.colorBgChip, null)
            layoutParams = ChipGroup.LayoutParams(
                ChipGroup.LayoutParams.WRAP_CONTENT,
                ChipGroup.LayoutParams.WRAP_CONTENT
            )
                .apply {
                    marginEnd = 10.dp
                }
        }
    }
}
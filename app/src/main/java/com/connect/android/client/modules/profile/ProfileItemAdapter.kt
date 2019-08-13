package com.connect.android.client.modules.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.connect.android.client.R
import com.connect.android.client.model.common.ProfileItem
import com.connect.android.client.modules.common.ProfileBinder

open class ProfileItemAdapter(
    private val layoutInflater: LayoutInflater
) :
    ListAdapter<ProfileItem, ProfileItemViewHolder>(
        object : DiffUtil.ItemCallback<ProfileItem>() {
            override fun areItemsTheSame(oldItem: ProfileItem, newItem: ProfileItem): Boolean {
                return oldItem.getIdentifier() == newItem.getIdentifier()
            }

            override fun areContentsTheSame(oldItem: ProfileItem, newItem: ProfileItem): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItemViewHolder {
        return ProfileItemViewHolder(
            layoutInflater.inflate(
                R.layout.adt_profile_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ProfileItemViewHolder, position: Int) {
        viewHolder.bindView(getItem(position))
    }
}

class ProfileItemViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView) {
    fun bindView(item: ProfileItem) {
        ProfileBinder.bindProfileItem(containerView, item)
    }
}


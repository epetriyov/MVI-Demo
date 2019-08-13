package com.connect.android.client.modules.myprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.connect.android.client.R
import com.connect.android.client.model.common.ProfileItem
import com.connect.android.client.modules.base.BaseViewHolder
import com.connect.android.client.modules.common.ProfileBinder
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

open class MyProfileItemAdapter(
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

    private val deleteSubject = PublishSubject.create<String>()

    fun itemDeletes(): Observable<String> = deleteSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItemViewHolder {
        return ProfileItemViewHolder(
            layoutInflater.inflate(
                R.layout.adt_bio_item,
                parent,
                false
            )
        )
            .apply {
                itemClicks().map { getItem(it).getIdentifier()!! }.subscribeWith(deleteSubject)
            }
    }

    override fun onBindViewHolder(viewHolder: ProfileItemViewHolder, position: Int) {
        viewHolder.bindView(getItem(position))
    }
}

class ProfileItemViewHolder(containerView: View) : BaseViewHolder(containerView) {

    fun bindView(item: ProfileItem) {
        ProfileBinder.bindProfileItem(containerView, item)
    }
}
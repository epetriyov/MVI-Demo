package com.connect.android.client.modules.recommendations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.connect.android.client.R
import com.connect.android.client.model.profile.User
import com.connect.android.client.modules.base.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RecommendationsAdapter(private val layoutInflater: LayoutInflater) :
    ListAdapter<User, RecommendationViewHolder>(
        object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    private var items: MutableList<User> = mutableListOf()

    private val clickSubject = PublishSubject.create<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        return RecommendationViewHolder(layoutInflater.inflate(R.layout.card_recommendation, parent, false))
            .apply { itemClicks().map { items[it] }.subscribeWith(clickSubject) }
    }

    override fun onBindViewHolder(holderRecommendation: RecommendationViewHolder, position: Int) {
        holderRecommendation.bindView(items[position])
    }

    fun addItems(items: List<User>) {
        this.items.addAll(items)
        submitList(this.items)
    }

    fun clicks(): Observable<User> = clickSubject

    fun getItemByPosition(position: Int): User {
        return items[position]
    }
}

class RecommendationViewHolder(containerView: View) : BaseViewHolder(containerView) {
    fun bindView(item: User) {
        RecommendationsBinder.bindRecommendation(containerView, item)
    }
}

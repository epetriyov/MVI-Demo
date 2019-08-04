package com.connect.android.client.modules.recommendations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.connect.android.client.R
import com.connect.android.client.model.profile.User
import com.connect.android.client.tools.glide.GlideApp
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RecommendationsAdapter(private val layoutInflater: LayoutInflater) :
    ListAdapter<User, ViewHolder>(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.card_recommendation, parent, false))
            .apply { itemClicks().map { items[it] }.subscribeWith(clickSubject) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
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

class ViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView) {
    fun bindView(item: User) {
        containerView.findViewById<TextView>(R.id.label_name).text = item.name
        GlideApp.with(containerView)
            .load(item.avatar)
            .centerCrop()
            .error(R.drawable.ic_placeholder)
            .into(containerView.findViewById(R.id.img_avatar))
        containerView.findViewById<TextView>(R.id.label_skills).text = item.skills?.joinToString()
        containerView.findViewById<TextView>(R.id.label_goals).text = item.goals?.joinToString()
        containerView.findViewById<TextView>(R.id.label_location).text =
            String.format(containerView.context.getString(R.string.distance), item.distance)
        containerView.findViewById<TextView>(R.id.label_work).text = item.getWorkInfo()
    }

    fun itemClicks(): Observable<Int> {
        return containerView.clicks().map { adapterPosition }.publish().autoConnect()
    }

}

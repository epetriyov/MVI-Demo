package com.connect.android.client.modules.profile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.connect.android.client.R
import com.connect.android.client.model.profile.ConnectionType
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.common.ProfileBinder
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_chat.view.btn_back
import kotlinx.android.synthetic.main.view_profile.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class ProfileView(context: Context, initialState: ProfileVS) :
    BaseMviView<ProfileVIA, ProfileVOA, ProfileVS>(context, initialState) {

    val profileViewModel: ProfileViewModel by inject { parametersOf(initialState) }

    val workLayoutManager: LinearLayoutManager by inject { parametersOf(context) }

    val educationLayoutManager: LinearLayoutManager by inject { parametersOf(context) }

    val workAdapter: ProfileItemAdapter by inject { parametersOf(context) }

    val educationAdapter: ProfileItemAdapter by inject { parametersOf(context) }

    override fun layoutId() = R.layout.view_profile

    override fun viewModel() = profileViewModel

    override fun initView(savedViewState: Bundle?) {
        aims_block.findViewById<TextView>(R.id.title_block).apply {
            setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.ic_aims), null, null, null)
            setText(R.string.title_profile_aims)
        }
        skills_block.findViewById<TextView>(R.id.title_block).apply {
            setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.ic_sphere), null, null, null)
            setText(R.string.title_profile_spheres)
        }
        jobs_block.findViewById<TextView>(R.id.title_list_block).apply {
            setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.ic_work), null, null, null)
            setText(R.string.work)
        }
        jobs_block.findViewById<RecyclerView>(R.id.recycler_list).apply {
            layoutManager = workLayoutManager
            adapter = workAdapter
        }
        education_block.findViewById<TextView>(R.id.title_list_block).apply {
            setCompoundDrawables(ContextCompat.getDrawable(context, R.drawable.ic_education), null, null, null)
            setText(R.string.education)
        }
        education_block.findViewById<RecyclerView>(R.id.recycler_list).apply {
            layoutManager = educationLayoutManager
            adapter = educationAdapter
        }
    }

    override fun inputActions(): List<Observable<out ProfileVIA>> = listOf(
        btn_chat.clicks().map { ProfileVIA.Chat }
    )

    override fun outputActions(): List<Observable<out ProfileVOA>> = listOf(
        btn_back.clicks().map { ProfileVOA.BackAction }
    )

    override fun bindState(viewState: ProfileVS) {
        viewState.user.bind {
            connect_indicator.isVisible = it.connectionType == ConnectionType.CONNECTED
            label_work.text = it.getWorkInfo()
            label_location.text = String.format(context.getString(R.string.distance), it.distance)
            ProfileBinder.bindProfile(this@ProfileView, it, workAdapter, educationAdapter)
        }
        viewState.chat.bind {
            outcomingAction(ProfileVOA.ChatAction(it))
        }
    }
}
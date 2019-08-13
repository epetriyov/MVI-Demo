package com.connect.android.client.modules.myprofile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.connect.android.client.R
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.common.Field
import com.connect.android.client.modules.common.ProfileBinder
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_my_profile.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class MyProfileView(context: Context, initialState: MyProfileVS) :
    BaseMviView<MyProfileVIA, MyProfileVOA, MyProfileVS>(context, initialState) {

    val myProfileViewModel: MyProfileViewModel by inject { parametersOf(initialState) }

    val workLayoutManager: LinearLayoutManager by inject { parametersOf(context) }

    val educationLayoutManager: LinearLayoutManager by inject { parametersOf(context) }

    val workAdapter: MyProfileItemAdapter by inject { parametersOf(context) }

    val educationAdapter: MyProfileItemAdapter by inject { parametersOf(context) }

    override fun layoutId() = R.layout.view_my_profile

    override fun viewModel() = myProfileViewModel

    override fun loadAction() = MyProfileVIA.Load

    override fun initView(savedViewState: Bundle?) {
        aims_block.findViewById<TextView>(R.id.title_block).apply {
            setText(R.string.title_profile_aims)
        }
        aims_block.findViewById<TextView>(R.id.title_info_description).apply {
            setText(R.string.aims_description)
        }
        skills_block.findViewById<TextView>(R.id.title_block).apply {
            setText(R.string.title_profile_spheres)
        }
        skills_block.findViewById<TextView>(R.id.title_info_description).apply {
            setText(R.string.spheres_description)
        }
        jobs_block.findViewById<TextView>(R.id.title_list_block).apply {
            setText(R.string.work)
        }
        jobs_block.findViewById<RecyclerView>(R.id.recycler_list).apply {
            layoutManager = workLayoutManager
            adapter = workAdapter
        }
        education_block.findViewById<TextView>(R.id.title_list_block).apply {
            setText(R.string.education)
        }
        education_block.findViewById<RecyclerView>(R.id.recycler_list).apply {
            layoutManager = educationLayoutManager
            adapter = educationAdapter
        }
    }

    override fun inputActions() = listOf(
        btn_exit.clicks().map { MyProfileVIA.Logout },
        Observable.merge(title_skills.clicks(), value_skills.clicks())
            .map { MyProfileVIA.EditSkills },
        Observable.merge(title_goals.clicks(), value_goals.clicks())
            .map { MyProfileVIA.EditGoals },
        jobs_block.findViewById<View>(R.id.title_list_block).clicks().map { MyProfileVIA.AddJob },
        education_block.findViewById<View>(R.id.title_list_block).clicks().map { MyProfileVIA.AddEducation },
        aims_block.clicks().map { MyProfileVIA.EditAims },
        skills_block.clicks().map { MyProfileVIA.EditSpheres },
        workAdapter.itemDeletes().map { MyProfileVIA.DeleteJob(it) },
        educationAdapter.itemDeletes().map { MyProfileVIA.DeleteEducation(it) }
    )

    override fun outputActions(): List<Observable<out MyProfileVOA>> = emptyList()

    override fun bindState(viewState: MyProfileVS) {
        viewState.user.bind {
            scroll_view.isVisible = true
            jobs_block.findViewById<RecyclerView>(R.id.recycler_list).isVisible = !it.works.isNullOrEmpty()
            education_block.findViewById<RecyclerView>(R.id.recycler_list).isVisible = !it.educations.isNullOrEmpty()
            ProfileBinder.bindProfile(this@MyProfileView, it, workAdapter, educationAdapter)
        }
        viewState.error.bind {
            showSnackbar(it)
        }
        viewState.addEducations.bind {
            outcomingAction(MyProfileVOA.EditProfileItem(field = Field.EDUCATION))
        }
        viewState.addWorks.bind {
            outcomingAction(MyProfileVOA.EditProfileItem(field = Field.WORK))
        }
        viewState.editEducations.bind { educationId ->
            outcomingAction(
                MyProfileVOA.EditProfileItem(
                    viewState.user.peekContent()!!.educations!!.first { it.id == educationId },
                    Field.EDUCATION,
                    educationId
                )
            )
        }
        viewState.editWorks.bind { workId ->
            outcomingAction(
                MyProfileVOA.EditProfileItem(
                    viewState.user.peekContent()!!.works!!.first { it.id == workId },
                    Field.WORK,
                    workId
                )
            )
        }
        viewState.editAims.bind {
            outcomingAction(MyProfileVOA.EditProfile(viewState.user.peekContent()!!, Field.AIMS))
        }
        viewState.editGoals.bind {
            outcomingAction(MyProfileVOA.EditProfile(viewState.user.peekContent()!!, Field.GOALS))
        }
        viewState.editSkills.bind {
            outcomingAction(MyProfileVOA.EditProfile(viewState.user.peekContent()!!, Field.SKILLS))
        }
        viewState.editSpheres.bind {
            outcomingAction(MyProfileVOA.EditProfile(viewState.user.peekContent()!!, Field.SPHERES))
        }
        viewState.logoutAction.bind {
            outcomingAction(MyProfileVOA.Logout)
        }
    }
}
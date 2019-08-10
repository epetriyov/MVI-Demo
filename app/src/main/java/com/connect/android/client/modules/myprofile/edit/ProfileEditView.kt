package com.connect.android.client.modules.myprofile.edit

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.view.isVisible
import com.connect.android.client.R
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.common.Field.*
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.view_edit_text.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class ProfileEditView(context: Context, initialState: ProfileEditVS) :
    BaseMviView<ProfileEditVIA, ProfileEditVOA, ProfileEditVS>(context, initialState) {

    val profileEditViewModel: ProfileEditViewModel by inject { parametersOf(initialState) }

    override fun layoutId() = R.layout.view_edit_text

    override fun viewModel() = profileEditViewModel

    override fun initView(savedViewState: Bundle?) {
    }

    override fun inputActions() = listOf(btn_save.clicks().map {
        ProfileEditVIA.Save(
            edit_text.text.toString()
        )
    })

    override fun outputActions() = listOf(btn_back.clicks().map { ProfileEditVOA.Back })

    override fun loadAction() = ProfileEditVIA.Init

    override fun bindState(viewState: ProfileEditVS) {
        with(viewState)
        {
            profile.bind {
                action_bar.setText(
                    when (field) {
                        SKILLS -> R.string.skills_title
                        GOALS -> R.string.goals_title
                        AIMS -> R.string.title_profile_aims
                        SPHERES -> R.string.title_profile_spheres
                        else -> -1
                    }
                )
                if (field in arrayOf(GOALS, AIMS, SPHERES)) {
                    edit_text.setHint(R.string.hint_tags)
                }
                edit_text.setText(
                    when (field) {
                        SKILLS -> it.skills?.joinToString(TAGS_SEPARATOR)
                        GOALS -> it.goals?.joinToString(TAGS_SEPARATOR)
                        AIMS -> it.about
                        SPHERES -> it.fields?.joinToString(TAGS_SEPARATOR)
                        else -> ""
                    }
                )
            }
            error.bind {
                showSnackbar(it)
            }
            saved.bind {
                outcomingAction(ProfileEditVOA.Saved)
            }
            this@ProfileEditView.progress.isVisible = progress
        }
    }
}
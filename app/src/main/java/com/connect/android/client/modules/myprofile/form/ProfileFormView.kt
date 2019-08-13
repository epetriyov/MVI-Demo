package com.connect.android.client.modules.myprofile.form

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.view.isVisible
import com.connect.android.client.R
import com.connect.android.client.extensions.showSnackbar
import com.connect.android.client.modules.base.BaseMviView
import com.connect.android.client.modules.common.Field
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.view_edit_form.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class ProfileFormView(context: Context, initialState: ProfileFormVS) :
    BaseMviView<ProfileFormVIA, ProfileFormVOA, ProfileFormVS>(context, initialState) {

    val profileFormViewModel: ProfileFormViewModel by inject { parametersOf(initialState) }

    override fun layoutId() = R.layout.view_edit_form

    override fun viewModel() = profileFormViewModel

    override fun initView(savedViewState: Bundle?) {
    }

    override fun inputActions() = listOf(
        btn_save.clicks().map {
            ProfileFormVIA.Save(
                edit_title.text.toString(),
                edit_spec.text.toString(),
                edit_start_year.text.toString(),
                edit_end_year.text.toString()
            )
        })

    override fun outputActions() = listOf(btn_back.clicks().map { ProfileFormVOA.Back })

    override fun loadAction() = ProfileFormVIA.Init

    override fun bindState(viewState: ProfileFormVS) {
        with(viewState)
        {
            when (field) {
                Field.EDUCATION -> {
                    action_bar.setText(R.string.education)
                    edit_title.setHint(R.string.education_name)
                    edit_spec.setHint(R.string.education_spec)
                    edit_start_year.setHint(R.string.education_start)
                    edit_end_year.setHint(R.string.education_end)
                }
                Field.WORK -> {
                    action_bar.setText(R.string.work)
                    edit_title.setHint(R.string.work_name)
                    edit_spec.setHint(R.string.work_position)
                    edit_start_year.setHint(R.string.work_start)
                    edit_end_year.setHint(R.string.work_end)
                }
            }
            profileItem?.bind {
                edit_title.setText(it.getFirstText())
                edit_spec.setText(it.getSecondText())
                edit_start_year.setText(it.getFromDate())
                edit_end_year.setText(it.getTillDate())
            }
            error.bind {
                showSnackbar(it)
            }
            saved.bind {
                outcomingAction(ProfileFormVOA.Saved)
            }
            this@ProfileFormView.progress.isVisible = progress
        }
    }

}
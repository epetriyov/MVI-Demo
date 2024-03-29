package com.connect.android.client.modules.myprofile.edit

import com.connect.android.client.extensions.onLoggableError
import com.connect.android.client.extensions.safeMessage
import com.connect.android.client.model.profile.ProfileRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.ESO
import com.connect.android.client.modules.base.withUpdate
import com.connect.android.client.modules.common.Field.*
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers

typealias ProfileEditSideEffect = SideEffect<ProfileEditVS, ProfileEditVIA>

class ProfileEditViewModel(
    private val profileRepository: ProfileRepository,
    initialState: ProfileEditVS
) : BaseMviViewModel<ProfileEditVIA, ProfileEditVS>(initialState) {

    private val saveProfile: ProfileEditSideEffect = { actions, viewState ->
        actions.ofType<ProfileEditVIA.Save>()
            .map {
                when (viewState().field) {
                    SKILLS -> viewState().profile.peekContent()!!.copyMe(skills = it.text.split(TAGS_SEPARATOR))
                    GOALS -> viewState().profile.peekContent()!!.copyMe(goals = it.text.split(TAGS_SEPARATOR))
                    AIMS -> viewState().profile.peekContent()!!.copyMe(about = it.text)
                    SPHERES -> viewState().profile.peekContent()!!.copyMe(fields = it.text.split(TAGS_SEPARATOR))
                    else -> viewState().profile.peekContent()!!
                }
            }
            .observeOn(Schedulers.io())
            .flatMap {
                profileRepository.updateProfile(it)
                    .andThen(profileRepository.fetchProfile())
                    .andThen(Observable.fromCallable { Unit })
                    .map { ProfileEditVIA.ProfileUpdated as ProfileEditVIA }
                    .onLoggableError { t ->
                        ProfileEditVIA.ProfileError(
                            t.safeMessage()
                        )
                    }
                    .startWith(ProfileEditVIA.ProfileProgress)
            }
    }

    override fun filterActions() = listOf(
        ProfileEditVIA.Init::class,
        ProfileEditVIA.ProfileError::class,
        ProfileEditVIA.ProfileUpdated::class,
        ProfileEditVIA.ProfileProgress::class
    )

    override fun reducer(state: ProfileEditVS, action: ProfileEditVIA): ProfileEditVS {
        return when (action) {
            is ProfileEditVIA.ProfileError -> state.copy(progress = false, error = action.error.withUpdate())
            ProfileEditVIA.ProfileUpdated -> state.copy(progress = false, saved = ESO.withUpdate())
            ProfileEditVIA.ProfileProgress -> state.copy(progress = true)
            else -> state
        }
    }

    override fun sideEffects() = listOf(saveProfile)
}
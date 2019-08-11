package com.connect.android.client.modules.myprofile.form

import com.connect.android.client.extensions.onLoggableError
import com.connect.android.client.extensions.safeMessage
import com.connect.android.client.model.educations.EducationData
import com.connect.android.client.model.educations.EducationsRepository
import com.connect.android.client.model.profile.ProfileRepository
import com.connect.android.client.model.works.WorkData
import com.connect.android.client.model.works.WorksRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.ESO
import com.connect.android.client.modules.base.withUpdate
import com.connect.android.client.modules.common.Field
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.rxkotlin.ofType
import io.reactivex.schedulers.Schedulers

typealias ProfileFormSideEffect = SideEffect<ProfileFormVS, ProfileFormVIA>

class ProfileFormViewModel(
    private val profileRepository: ProfileRepository,
    private val worksRepository: WorksRepository,
    private val educationsRepository: EducationsRepository,
    initialState: ProfileFormVS
) : BaseMviViewModel<ProfileFormVIA, ProfileFormVS>(initialState) {

    private val updateProfileTransformer = ObservableTransformer<Unit, ProfileFormVIA> {
        it
            .observeOn(Schedulers.io())
            .flatMap {
            profileRepository.fetchProfile()
                .andThen(Observable.fromCallable { Unit })
        }
            .map { ProfileFormVIA.ProfileUpdated as ProfileFormVIA }
            .onLoggableError { t -> ProfileFormVIA.ProfileError(t.safeMessage()) }
            .startWith(ProfileFormVIA.ProfileProgress)
    }

    private val editWork: ProfileFormSideEffect = { actions, viewState ->
        actions.ofType<ProfileFormVIA.Save>()
            .filter { viewState().id != null && viewState().profileItem != null }
            .filter { viewState().field == Field.WORK }
            .map {
                (viewState().profileItem!!.peekContent()!! as WorkData).copy(
                    company = it.title,
                    description = it.spec,
                    yearFrom = it.startYear.toInt(),
                    yearTill = it.endYear.toInt()
                )
            }
            .observeOn(Schedulers.io())
            .flatMap {
                worksRepository.updateWork(viewState().id!!, it)
                    .andThen(Observable.fromCallable { Unit })
                    .compose(updateProfileTransformer)
            }
    }

    private val addWork: ProfileFormSideEffect = { actions, viewState ->
        actions.ofType<ProfileFormVIA.Save>()
            .filter { viewState().id == null }
            .filter { viewState().field == Field.WORK }
            .map {
                WorkData(
                    company = it.title,
                    description = it.spec,
                    yearFrom = it.startYear.toInt(),
                    yearTill = it.endYear.toInt()
                )
            }
            .observeOn(Schedulers.io())
            .flatMap {
                worksRepository.addWork(it)
                    .andThen(Observable.fromCallable { Unit })
                    .compose(updateProfileTransformer)
            }
    }

    private val editEducation: ProfileFormSideEffect = { actions, viewState ->
        actions.ofType<ProfileFormVIA.Save>()
            .filter { viewState().id != null && viewState().profileItem != null }
            .filter { viewState().field == Field.EDUCATION }
            .map {
                (viewState().profileItem!!.peekContent()!! as EducationData).copy(
                    name = it.title,
                    faculty = it.spec,
                    yearFrom = it.startYear.toInt(),
                    yearTill = it.endYear.toInt()
                )
            }
            .observeOn(Schedulers.io())
            .flatMap {
                educationsRepository.updateEducation(viewState().id!!, it)
                    .andThen(Observable.fromCallable { Unit })
                    .compose(updateProfileTransformer)
            }
    }

    private val addEducation: ProfileFormSideEffect = { actions, viewState ->
        actions.ofType<ProfileFormVIA.Save>()
            .filter { viewState().id != null }
            .filter { viewState().field == Field.EDUCATION }
            .map {
                EducationData(
                    name = it.title,
                    faculty = it.spec,
                    yearFrom = it.startYear.toInt(),
                    yearTill = it.endYear.toInt()
                )
            }
            .observeOn(Schedulers.io())
            .flatMap {
                educationsRepository.addEducation(it)
                    .andThen(Observable.fromCallable { Unit })
                    .compose(updateProfileTransformer)
            }
    }

    override fun filterActions() = listOf(
        ProfileFormVIA.Init::class,
        ProfileFormVIA.ProfileError::class,
        ProfileFormVIA.ProfileUpdated::class,
        ProfileFormVIA.ProfileProgress::class
    )

    override fun reducer(state: ProfileFormVS, action: ProfileFormVIA): ProfileFormVS {
        return when (action) {
            is ProfileFormVIA.ProfileError -> state.copy(progress = false, error = action.error.withUpdate())
            ProfileFormVIA.ProfileUpdated -> state.copy(progress = false, saved = ESO.withUpdate())
            ProfileFormVIA.ProfileProgress -> state.copy(progress = true)
            else -> state
        }
    }

    override fun sideEffects() = listOf(editWork, editEducation, addWork, addEducation)

}
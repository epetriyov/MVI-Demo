package com.connect.android.client.modules.myprofile

import com.connect.android.client.extensions.onLoggableError
import com.connect.android.client.extensions.safeMessage
import com.connect.android.client.model.auth.AuthRepository
import com.connect.android.client.model.educations.EducationsRepository
import com.connect.android.client.model.profile.ProfileRepository
import com.connect.android.client.model.works.WorksRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.connect.android.client.modules.base.ESO
import com.connect.android.client.modules.base.withUpdate
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType

typealias MyProfileSideEffect = SideEffect<MyProfileVS, MyProfileVIA>

class MyProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
    private val worksRepository: WorksRepository,
    private val educationsRepository: EducationsRepository,
    initialState: MyProfileVS
) :
    BaseMviViewModel<MyProfileVIA, MyProfileVS>(initialState) {

    private val loadProfile: MyProfileSideEffect = { actions, _ ->
        actions.ofType<MyProfileVIA.Load>()
            .flatMap {
                profileRepository.loadProfile().toObservable()
                    .map { MyProfileVIA.ProfileLoaded(it) }
            }
    }

    private val fetchProfile: MyProfileSideEffect = { actions, _ ->
        actions.ofType<MyProfileVIA.Load>()
            .flatMap {
                profileRepository.fetchProfile()
                    .andThen(Observable.just(Unit))
                    .map { MyProfileVIA.ProfileUpdated as MyProfileVIA }
                    .onLoggableError { t -> MyProfileVIA.Error(t.safeMessage()) }
                    .startWith(MyProfileVIA.UpdateProgress)
            }
    }

    private val logout: MyProfileSideEffect = { actions, _ ->
        actions.ofType<MyProfileVIA.Logout>()
            .doOnNext { authRepository.logout() }
            .map { MyProfileVIA.LogoutFinished }
    }

    private val deleteJob: MyProfileSideEffect = { actions, _ ->
        actions.ofType<MyProfileVIA.DeleteJob>()
            .flatMap {
                worksRepository.removeWork(it.jobId)
                    .andThen(profileRepository.fetchProfile())
                    .andThen(Observable.just(Unit))
                    .map { MyProfileVIA.ProfileUpdated as MyProfileVIA }
                    .onLoggableError { t -> MyProfileVIA.Error(t.safeMessage()) }
                    .startWith(MyProfileVIA.UpdateProgress)
            }
    }

    private val deleteEducation: MyProfileSideEffect = { actions, _ ->
        actions.ofType<MyProfileVIA.DeleteEducation>()
            .flatMap {
                educationsRepository.removeEducation(it.jobId)
                    .andThen(profileRepository.fetchProfile())
                    .andThen(Observable.just(Unit))
                    .map { MyProfileVIA.ProfileUpdated as MyProfileVIA }
                    .onLoggableError { t -> MyProfileVIA.Error(t.safeMessage()) }
                    .startWith(MyProfileVIA.UpdateProgress)
            }
    }

    override fun filterActions() = listOf(
        MyProfileVIA.Error::class,
        MyProfileVIA.ProfileLoaded::class,
        MyProfileVIA.ProfileUpdated::class,
        MyProfileVIA.LogoutFinished::class,
        MyProfileVIA.UpdateProgress::class,
        MyProfileVIA.EditSkills::class,
        MyProfileVIA.EditGoals::class,
        MyProfileVIA.AddEducation::class,
        MyProfileVIA.AddJob::class,
        MyProfileVIA.EditAims::class,
        MyProfileVIA.EditSpheres::class,
        MyProfileVIA.EditJob::class,
        MyProfileVIA.EditEducation::class,
        MyProfileVIA.DeleteEducation::class,
        MyProfileVIA.DeleteJob::class
    )

    override fun reducer(state: MyProfileVS, action: MyProfileVIA): MyProfileVS {
        return when (action) {
            is MyProfileVIA.Error -> state.copy(loadProgress = false, error = action.message.withUpdate())
            is MyProfileVIA.ProfileLoaded -> state.copy(user = action.user.withUpdate())
            MyProfileVIA.ProfileUpdated -> state.copy(loadProgress = false)
            MyProfileVIA.LogoutFinished -> state.copy(logoutAction = ESO.withUpdate())
            MyProfileVIA.UpdateProgress -> state.copy(loadProgress = true)
            MyProfileVIA.EditGoals -> state.copy(editGoals = ESO.withUpdate())
            MyProfileVIA.EditSkills -> state.copy(editSkills = ESO.withUpdate())
            MyProfileVIA.AddJob -> state.copy(addWorks = ESO.withUpdate())
            MyProfileVIA.AddEducation -> state.copy(addEducations = ESO.withUpdate())
            MyProfileVIA.EditAims -> state.copy(editAims = ESO.withUpdate())
            MyProfileVIA.EditSpheres -> state.copy(editSpheres = ESO.withUpdate())
            is MyProfileVIA.EditJob -> state.copy(editWorks = action.jobId.withUpdate())
            is MyProfileVIA.EditEducation -> state.copy(editEducations = action.jobId.withUpdate())
            else -> state
        }
    }

    override fun sideEffects() = listOf(loadProfile, fetchProfile, logout, deleteJob, deleteEducation)
}
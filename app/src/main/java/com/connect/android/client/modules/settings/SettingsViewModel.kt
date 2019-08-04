package com.connect.android.client.modules.settings

import com.connect.android.client.model.auth.AuthRepository
import com.connect.android.client.model.profile.ProfileRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.freeletics.rxredux.SideEffect
import kotlin.reflect.KClass

class SettingsViewModel(private val profileRepository: ProfileRepository,
                        private val authRepository: AuthRepository,
                        initialState: SettingsVS): BaseMviViewModel<SettingsVIA,SettingsVS>(initialState) {
    override fun filterActions(): List<KClass<out SettingsVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reducer(state: SettingsVS, action: SettingsVIA): SettingsVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<SettingsVS, SettingsVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
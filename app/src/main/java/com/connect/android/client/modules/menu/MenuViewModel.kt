package com.connect.android.client.modules.menu

import com.connect.android.client.model.profile.ProfileRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.freeletics.rxredux.SideEffect

class MenuViewModel(
    private val profileRepository: ProfileRepository,
    initialState: MenuVS
) : BaseMviViewModel<MenuVIA, MenuVS>(initialState) {
    override fun reducer(state: MenuVS, action: MenuVIA): MenuVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<MenuVS, MenuVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
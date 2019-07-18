package com.connect.android.client.modules.myprofile

import com.connect.android.client.model.profile.ProfileRepository
import com.connect.android.client.modules.base.BaseMviViewModel
import com.freeletics.rxredux.SideEffect

class MyProfileViewModel(private val profileRepository: ProfileRepository, initialState: MyProfileVS) :
    BaseMviViewModel<MyProfileVIA, MyProfileVS>(initialState) {
    override fun reducer(state: MyProfileVS, action: MyProfileVIA): MyProfileVS {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sideEffects(): List<SideEffect<MyProfileVS, MyProfileVIA>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
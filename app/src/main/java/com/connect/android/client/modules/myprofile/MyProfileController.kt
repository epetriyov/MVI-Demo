package com.connect.android.client.modules.myprofile

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.Do
import com.connect.android.client.extensions.buildRouterTransaction
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.myprofile.edit.ProfileEditController
import com.connect.android.client.modules.myprofile.form.ProfileFormController

class MyProfileController(bundle: Bundle? = null) :
    BaseMviController<MyProfileView, MyProfileVIA, MyProfileVOA>(bundle) {
    override fun buildView(context: Context) = MyProfileView(context, MyProfileVS())

    override fun handleViewEvents(action: MyProfileVOA) {
        val transaction = Do exhaustive when (action) {
            is MyProfileVOA.EditProfile -> ProfileEditController.newInstance(action.me, action.field)
            is MyProfileVOA.EditProfileItem -> ProfileFormController.newInstance(
                action.profileItem,
                action.field,
                action.id
            )
            MyProfileVOA.Logout ->
                //TODO logout
                targetController!!
        }.buildRouterTransaction()
        router.pushController(transaction!!)
    }
}
package com.connect.android.client.modules.profile_edit

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.BundleBuilder
import com.connect.android.client.extensions.Do
import com.connect.android.client.model.profile.Me
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.base.withUpdate
import com.connect.android.client.modules.common.Field

class ProfileEditController(bundle: Bundle? = null) :
    BaseMviController<ProfileEditView, ProfileEditVIA, ProfileEditVOA>(bundle) {

    companion object {
        private const val EXTRA_USER = "extra_user"

        private const val EXTRA_FIELD = "extra_field"

        fun newInstance(me: Me, field: Field): ProfileEditController {
            return ProfileEditController(
                BundleBuilder(Bundle())
                    .putSerializable(EXTRA_USER, me)
                    .putSerializable(EXTRA_FIELD, field)
                    .build()
            )
        }
    }

    override fun handleViewEvents(action: ProfileEditVOA) {
        Do exhaustive when (action) {
            ProfileEditVOA.Back -> router.handleBack()
            ProfileEditVOA.Saved -> router.handleBack()
        }
    }

    override fun buildView(context: Context) = ProfileEditView(
        context, ProfileEditVS(
            (args.getSerializable(EXTRA_USER) as Me).withUpdate(),
            args.getSerializable(EXTRA_FIELD) as Field
        )
    )
}
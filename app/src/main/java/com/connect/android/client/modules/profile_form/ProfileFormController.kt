package com.connect.android.client.modules.profile_form

import android.content.Context
import android.os.Bundle
import com.connect.android.client.extensions.BundleBuilder
import com.connect.android.client.extensions.Do
import com.connect.android.client.model.common.ProfileItem
import com.connect.android.client.modules.base.BaseMviController
import com.connect.android.client.modules.base.withUpdate
import com.connect.android.client.modules.common.Field

class ProfileFormController(bundle: Bundle? = null) :
    BaseMviController<ProfileFormView, ProfileFormVIA, ProfileFormVOA>(bundle) {

    companion object {
        private const val EXTRA_PROFILE_ITEM = "extra_profile_item"
        private const val EXTRA_FIELD = "extra_field"
        private const val EXTRA_ID = "extra_id"

        fun newInstance(profileItem: ProfileItem?, field: Field, id: String?): ProfileFormController {
            return ProfileFormController(
                BundleBuilder(Bundle())
                    .putSerializable(EXTRA_FIELD, field)
                    .putString(EXTRA_ID, id)
                    .apply {
                        profileItem?.let { putSerializable(EXTRA_PROFILE_ITEM, it) }
                    }
                    .build()
            )
        }
    }

    override fun buildView(context: Context) = ProfileFormView(
        context, ProfileFormVS(
            (args.getSerializable(EXTRA_PROFILE_ITEM) as? ProfileItem)?.withUpdate(),
            args.getSerializable(EXTRA_FIELD) as Field,
            args.getString(EXTRA_ID)!!
        )
    )

    override fun handleViewEvents(action: ProfileFormVOA) {
        Do exhaustive when (action) {
            ProfileFormVOA.Back -> router.handleBack()
            ProfileFormVOA.Saved -> router.handleBack()
        }
    }
}
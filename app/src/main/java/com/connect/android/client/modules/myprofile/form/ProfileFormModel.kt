package com.connect.android.client.modules.myprofile.form

import com.connect.android.client.model.common.ProfileItem
import com.connect.android.client.modules.base.ORField
import com.connect.android.client.modules.base.ViewInputAction
import com.connect.android.client.modules.base.ViewOutputAction
import com.connect.android.client.modules.base.ViewState
import com.connect.android.client.modules.common.Field
import kotlinx.android.parcel.Parcelize

sealed class ProfileFormVIA : ViewInputAction() {
    data class Save(val title: String, val spec: String, val startYear: String, val endYear: String) :
        ProfileFormVIA()

    data class ProfileError(val error: String) : ProfileFormVIA()
    object ProfileUpdated : ProfileFormVIA()
    object ProfileProgress : ProfileFormVIA()
    object Init : ProfileFormVIA()
}

sealed class ProfileFormVOA : ViewOutputAction() {
    object Back : ProfileFormVOA()
    object Saved : ProfileFormVOA()
}

@Parcelize
data class ProfileFormVS(
    val profileItem: ORField<ProfileItem>? = ORField.empty(), val field: Field, val id: String?,
    val error: ORField<String> = ORField.empty(), val progress: Boolean = false,
    val saved: ORField<Unit> = ORField.empty()
) : ViewState()
package com.connect.android.client.modules.myprofile.edit

import com.connect.android.client.model.profile.Me
import com.connect.android.client.modules.base.*
import com.connect.android.client.modules.common.Field
import kotlinx.android.parcel.Parcelize

const val TAGS_SEPARATOR = ","

sealed class ProfileEditVIA : ViewInputAction() {
    data class Save(val text: String) : ProfileEditVIA()
    data class ProfileError(val error: String) : ProfileEditVIA()
    object ProfileUpdated : ProfileEditVIA()
    object ProfileProgress : ProfileEditVIA()
    object Init : ProfileEditVIA()
}

sealed class ProfileEditVOA : ViewOutputAction() {
    object Back : ProfileEditVOA()
    object Saved : ProfileEditVOA()
}

@Parcelize
data class ProfileEditVS(
    val profile: ORField<Me>, val field: Field,
    val error: ORField<String> = ORField.empty(), val progress: Boolean = false,
    val saved: ORField<ESO> = ORField.empty()
) : ViewState()
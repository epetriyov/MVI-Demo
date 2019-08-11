package com.connect.android.client.modules.myprofile

import com.connect.android.client.model.common.ProfileItem
import com.connect.android.client.model.profile.Me
import com.connect.android.client.modules.base.*
import com.connect.android.client.modules.common.Field
import kotlinx.android.parcel.Parcelize

sealed class MyProfileVIA : ViewInputAction() {
    object Load : MyProfileVIA()
    object ProfileUpdated : MyProfileVIA()
    object UpdateProgress : MyProfileVIA()
    object Logout : MyProfileVIA()
    object LogoutFinished : MyProfileVIA()
    object EditSkills : MyProfileVIA()
    object EditGoals : MyProfileVIA()
    object EditAims : MyProfileVIA()
    object EditSpheres : MyProfileVIA()
    object AddJob : MyProfileVIA()
    object AddEducation : MyProfileVIA()
    data class EditJob(val jobId: String) : MyProfileVIA()
    data class EditEducation(val jobId: String) : MyProfileVIA()
    data class DeleteJob(val jobId: String) : MyProfileVIA()
    data class DeleteEducation(val jobId: String) : MyProfileVIA()
    data class ProfileLoaded(val user: Me) : MyProfileVIA()
    data class Error(val message: String) : MyProfileVIA()
}

sealed class MyProfileVOA : ViewOutputAction() {
    data class EditProfile(val me: Me, val field: Field) : MyProfileVOA()
    data class EditProfileItem(val profileItem: ProfileItem? = null, val field: Field, val id: String? = null) : MyProfileVOA()
}

@Parcelize
data class MyProfileVS(
    val user: ORField<Me> = ORField.empty(),
    val error: ORField<String> = ORField.empty(),
    val loadProgress: Boolean = false,
    val logoutAction: ORField<ESO> = ORField.empty(),
    val editSkills: ORField<ESO> = ORField.empty(),
    val editGoals: ORField<ESO> = ORField.empty(),
    val editAims: ORField<ESO> = ORField.empty(),
    val editSpheres: ORField<ESO> = ORField.empty(),
    val addWorks: ORField<ESO> = ORField.empty(),
    val addEducations: ORField<ESO> = ORField.empty(),
    val editWorks: ORField<String> = ORField.empty(),
    val editEducations: ORField<String> = ORField.empty()
) : ViewState()
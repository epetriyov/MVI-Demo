package com.connect.android.client.model.profile

import android.text.TextUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.connect.android.client.model.educations.EducationData
import com.connect.android.client.model.works.WorkData
import com.google.gson.annotations.SerializedName

data class AvatarResponse(val url: String)

data class ConnectResponse(val status: String)

data class FieldsResponse(val totalCount: Int?, val data: List<String>?)

@Entity(tableName = "contacts")
data class User(
    var name: String,
    var avatarMedium: String? = null,
    @PrimaryKey
    var id: String,
    var gender: String? = null,
    var about: String? = null,
    var skills: List<String>? = null,
    var goals: List<String>? = null,
    var fields: List<String>? = null,
    var avatar: String? = null,
    var avatarBig: String? = null,
    var works: List<WorkData>? = null,
    var educations: List<EducationData>? = null,
    var distance: Double? = null,
    @SerializedName("object")
    var obj: String = "user",
    var connectionType: ConnectionType? = null,
    var settings: Settings? = null
) {

    fun getWorkInfo(): String? {
        val work = works?.let {
            if (works!!.size > 0) {
                (works!![0]?.company ?: "") +
                        (if (!TextUtils.isEmpty(works!![0]?.company)
                            && !TextUtils.isEmpty(works!![0]?.position)
                        ) ", " else "") +
                        works!![0]?.position
            } else {
                ""
            }
        }
        return work
    }
}

data class Settings(
    @SerializedName("SHOW_DISTANCE")
    var showDistance: String? = "Y",
    @SerializedName("SHOW_RATIING")
    var showRating: String? = "Y",
    @SerializedName("SHOW_AGE")
    var showAge: String? = "Y",
    @SerializedName("AGE_MIN")
    var minAge: Int? = 18,
    @SerializedName("AGE_MAX")
    var maxAge: Int? = 100,
    @SerializedName("DISTANCE_MAX")
    var maxDistance: Int? = 100
) {

    fun isShowDistance(): Boolean {
        return showDistance == "Y"
    }

    fun isShowRating(): Boolean {
        return showRating == "Y"
    }

    fun isShowAge(): Boolean {
        return showAge == "Y"
    }

    fun setShowDistance(checked: Boolean) {
        showDistance = if (checked) "Y" else "N"
    }

    fun setShowRating(checked: Boolean) {
        showRating = if (checked) "Y" else "N"
    }

    fun setShowAge(checked: Boolean) {
        showAge = if (checked) "Y" else "N"
    }

}

enum class ConnectionType {
    CONNECTING, CONNECTED, DECLINED, RECOMMENDED
}
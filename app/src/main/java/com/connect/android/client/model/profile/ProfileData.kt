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
data class User @JvmOverloads constructor(
    val name: String,
    val avatarMedium: String? = null,
    @PrimaryKey
    val id: String,
    val gender: String? = null,
    val about: String? = null,
    val skills: List<String>? = null,
    val goals: List<String>? = null,
    val fields: List<String>? = null,
    val avatar: String? = null,
    val avatarBig: String? = null,
    val works: List<WorkData>? = null,
    val educations: List<EducationData>? = null,
    val distance: Double? = null,
    @SerializedName("object")
    val obj: String = "user",
    val connectionType: ConnectionType? = null,
    var settings: Settings? = null
) {

    fun getWorkInfo(): String? {
        val work = works?.let {
            if (works.isNotEmpty()) {
                (works[0].company ?: "") +
                        (if (!TextUtils.isEmpty(works[0].company)
                            && !TextUtils.isEmpty(works[0].position)
                        ) ", " else "") +
                        works[0].position
            } else {
                ""
            }
        }
        return work
    }
}

data class Settings(
    @SerializedName("SHOW_DISTANCE")
    val showDistance: String? = "Y",
    @SerializedName("SHOW_RATIING")
    val showRating: String? = "Y",
    @SerializedName("SHOW_AGE")
    val showAge: String? = "Y",
    @SerializedName("AGE_MIN")
    val minAge: Int? = 18,
    @SerializedName("AGE_MAX")
    val maxAge: Int? = 100,
    @SerializedName("DISTANCE_MAX")
    val maxDistance: Int? = 100
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

}

enum class ConnectionType {
    CONNECTING, CONNECTED, DECLINED, RECOMMENDED
}
package com.connect.android.client.model.educations

import com.connect.android.client.model.common.ProfileItem
import com.squareup.moshi.Json

/**
 * Created by evgenii on 5/8/17.
 */
data class EducationData(
    var id: String? = null,
    var name: String,
    var faculty: String? = null,
    var description: String? = null,
    var yearFrom: Int = 0,
    var yearTill: Int = 0,
    @Json(name = "object") var obj: String = "education"
) : ProfileItem {
    override fun copyFrom(item: ProfileItem) {
        if (item is EducationData) {
            name = item.name
            faculty = item.faculty
            description = item.description
            yearFrom = item.yearFrom
            yearTill = item.yearTill
        }
    }

    override fun getFirstText(): String? {
        return name
    }

    override fun getSecondText(): String? {
        return faculty
    }
}

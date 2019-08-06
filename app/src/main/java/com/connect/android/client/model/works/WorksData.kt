package com.connect.android.client.model.works

import com.connect.android.client.model.common.ProfileItem
import com.squareup.moshi.Json

/**
 * Created by evgenii on 5/8/17.
 */
data class WorkData(
    var id: String? = null,
    var company: String? = null,
    var position: String? = null,
    var city: String? = null,
    var description: String? = null,
    var yearTill: Int = 0,
    var yearFrom: Int = 0,
    @Json(name = "object")
    var obj: String = "work",
    var isCurrent: Boolean = false
) : ProfileItem {
    override fun getIdentifier(): String? {
        return id
    }

    override fun getDates(): String {
        return "$yearFrom - $yearTill"
    }

    override fun copyFrom(item: ProfileItem) {
        if (item is WorkData) {
            company = item.company
            position = item.position
            city = item.city
            description = item.description
            yearFrom = item.yearFrom
            yearTill = item.yearTill
        }
    }

    override fun getFirstText(): String? {
        return company
    }

    override fun getSecondText(): String? {
        return position
    }
}
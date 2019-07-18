package com.connect.android.client.model.contacts

import com.connect.android.client.model.common.ListData
import com.connect.android.client.model.profile.User
import io.reactivex.Maybe
import retrofit2.http.GET

interface ContactsApi {
    @GET("/users/connected")
    fun getContacts(): Maybe<ListData<User>>
}
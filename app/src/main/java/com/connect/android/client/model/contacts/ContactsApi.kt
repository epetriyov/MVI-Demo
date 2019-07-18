package com.connect.android.client.model.contacts

import com.connect.android.client.model.ListData
import com.connect.android.client.model.profile.User
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ContactsApi {
    @GET("/users/connected")
    fun getContacts(): Maybe<ListData<User>>
}
package com.connect.android.client.model.educations

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface EducationsApi {
    @DELETE("/educations/{id}")
    fun removeEducation(@Path("id") educationId: String?): Completable

    @POST("/educations/{id}")
    fun updateEducation(@Path("id") educationId: String?, @Body educationData: EducationData): Completable

    @POST("/educations")
    fun addEducation(@Body educationData: EducationData): Completable
}
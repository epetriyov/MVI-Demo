package com.connect.android.client.model.educations

import io.reactivex.Completable

interface EducationsRepository {

    fun addEducation(education: EducationData): Completable

    fun updateEducation(education: EducationData): Completable

    fun removeEducation(educationId: String): Completable
}
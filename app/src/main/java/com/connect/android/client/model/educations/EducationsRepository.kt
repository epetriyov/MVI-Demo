package com.connect.android.client.model.educations

import io.reactivex.Completable

interface EducationsRepository {

    fun addEducation(education: EducationData): Completable

    fun updateEducation(educationId: String, education: EducationData): Completable

    fun removeEducation(educationId: String): Completable
}

class EducationsRepoImpl(private val educationsApi: EducationsApi) : EducationsRepository {
    override fun addEducation(education: EducationData): Completable {
        return educationsApi.addEducation(education)
    }

    override fun updateEducation(educationId: String, education: EducationData): Completable {
        return educationsApi.updateEducation(educationId, education)
    }

    override fun removeEducation(educationId: String): Completable {
        return educationsApi.removeEducation(educationId)
    }

}
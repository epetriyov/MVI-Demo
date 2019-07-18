package com.connect.android.client.model.educations

import io.reactivex.Completable

interface EducationsRepository {

    fun addEducation(education: EducationData): Completable

    fun updateEducation(education: EducationData): Completable

    fun removeEducation(educationId: String): Completable
}

class EducationsRepoImpl(private val educationsApi: EducationsApi) : EducationsRepository {
    override fun addEducation(education: EducationData): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateEducation(education: EducationData): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeEducation(educationId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
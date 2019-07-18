package com.connect.android.client.model.works

import io.reactivex.Completable

interface WorksRepository {
    fun addWork(work: WorkData): Completable

    fun updateWork(work: WorkData): Completable

    fun removeWork(workId: String): Completable
}

class WorksRepoImpl(private val worksApi: WorksApi) : WorksRepository {
    override fun addWork(work: WorkData): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateWork(work: WorkData): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeWork(workId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
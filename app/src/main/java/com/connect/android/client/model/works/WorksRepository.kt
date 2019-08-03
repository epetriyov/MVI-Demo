package com.connect.android.client.model.works

import io.reactivex.Completable

interface WorksRepository {
    fun addWork(work: WorkData): Completable

    fun updateWork(workId: String, work: WorkData): Completable

    fun removeWork(workId: String): Completable
}

class WorksRepoImpl(private val worksApi: WorksApi) : WorksRepository {
    override fun addWork(work: WorkData): Completable {
        return worksApi.addWork(work)
    }

    override fun updateWork(workId: String, work: WorkData): Completable {
        return worksApi.updateWork(workId, work)
    }

    override fun removeWork(workId: String): Completable {
        return worksApi.removeWork(workId)
    }

}
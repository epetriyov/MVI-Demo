package com.connect.android.client.model.works

import io.reactivex.Completable

interface WorksRepository {
    fun addWork(work: WorkData): Completable

    fun updateWork(work: WorkData): Completable

    fun removeWork(workId: String): Completable
}
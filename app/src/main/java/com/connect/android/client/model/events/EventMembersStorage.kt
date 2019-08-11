package com.connect.android.client.model.events

import com.connect.android.client.model.profile.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface EventMembersStorage {
    fun getEventMembers(query: String?): Observable<List<User>>
    fun updateEventMembers(it: List<User>): Completable
}

class RamEventsMembersStorage : EventMembersStorage {

    private val eventMembers = BehaviorSubject.create<List<User>>()

    override fun getEventMembers(query: String?): Observable<List<User>> {
        return eventMembers.publish {
            Observable.merge(
                it.filter { query.isNullOrEmpty() },
                it.filter { !query.isNullOrEmpty() }.map {
                    it.filter {
                        it.about?.contains(query!!, ignoreCase = true) == true ||
                                it.skills?.firstOrNull { it.contains(query!!, ignoreCase = true) } != null ||
                                it.name.contains(query!!, ignoreCase = true) ||
                                it.works?.firstOrNull { it.company?.contains(query, ignoreCase = true) ?: false } != null
                    }
                }
            )
        }
    }

    override fun updateEventMembers(it: List<User>): Completable {
        return Completable.fromCallable { eventMembers.onNext(it) }
    }

}
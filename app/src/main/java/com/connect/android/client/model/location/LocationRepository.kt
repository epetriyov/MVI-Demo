package com.connect.android.client.model.location

import io.reactivex.Completable

interface LocationRepository {

    fun updateCurrentLocation(): Completable
}

class LocationRepoImpl(private val locationProvider: LocationProvider, private val locationApi: LocationApi) :
    LocationRepository {
    override fun updateCurrentLocation(): Completable {
        return locationProvider.provideLastLocation().flatMapCompletable {
            locationApi.sendLocation(LocationData(listOf(it.latitude, it.longitude)))
        }
    }

}
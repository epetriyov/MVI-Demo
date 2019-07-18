package com.connect.android.client.model.location

interface LocationRepository {

    fun updateCurrentLocation()
}

class LocationRepoImpl(private val locationProvider: LocationProvider, private val locationApi: LocationApi) :
    LocationRepository {
    override fun updateCurrentLocation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
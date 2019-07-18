package com.connect.android.client.model.location

import android.location.Location
import io.reactivex.Observable

interface LocationProvider {

    fun provideLastLocation(): Observable<Location>
}
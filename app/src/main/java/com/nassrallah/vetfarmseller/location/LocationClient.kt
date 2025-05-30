package com.nassrallah.vetfarmseller.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    fun getLastKnownLocation(): Flow<Location>

}
package org.architect_course.model

import android.Manifest
import android.app.Application
import android.location.Geocoder
import android.location.Location
import org.architect_course.ui.common.getFromLocationCompat

class RegionRepository(application: Application) {
    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val locationDataSource: LocationDataSource = PlayServicesLocationDataSource(application)
    private val coarsePermissionChecker = PermissionChecker(
        application,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val geocoder = Geocoder(application)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.check()
        return if (success) locationDataSource.findLastLocation() else null
    }

    private suspend fun Location?.toRegion(): String {
        val addresses = this?.let {
            geocoder.getFromLocationCompat(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}
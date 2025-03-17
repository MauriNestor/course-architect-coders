package org.architect_course.model

import android.app.Application
import org.architect_course.R

class MoviesRepository(application: Application) {
    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository = RegionRepository(application)

    suspend fun findPopularMovies() =
        RemoteConnection.service
            .listPopularMovies(
                apiKey,
                regionRepository.findLastRegion()
            )
}
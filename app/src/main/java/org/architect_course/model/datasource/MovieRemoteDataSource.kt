package org.architect_course.model.datasource

import org.architect_course.model.RemoteConnection


class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun findPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)
}
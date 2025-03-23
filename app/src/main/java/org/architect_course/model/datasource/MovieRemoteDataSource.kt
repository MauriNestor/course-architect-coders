package org.architect_course.model.datasource

import org.architect_course.model.RegionRepository
import org.architect_course.model.RemoteConnection


class MovieRemoteDataSource(private val apiKey: String, private val regionRepository: RegionRepository) {

    suspend fun findPopularMovies() =
        RemoteConnection.service
            .listPopularMovies(
                apiKey,
                regionRepository.findLastRegion()
            )
}
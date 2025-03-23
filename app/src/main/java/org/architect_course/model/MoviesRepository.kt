package org.architect_course.model


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.architect_course.App
import org.architect_course.R
import org.architect_course.model.database.Movie
import org.architect_course.model.datasource.MovieLocalDataSource
import org.architect_course.model.datasource.MovieRemoteDataSource

class MoviesRepository(application: App) {
    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        application.getString(R.string.api_key),
        regionRepository = RegionRepository(application)
    )
    val popularMovies = localDataSource.movies

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies()
            localDataSource.save(movies.results.toLocalModel())
        }
    }
}
private fun List<RemoteMovie>.toLocalModel(): List<Movie> = map { it.toLocalModel() }

private fun RemoteMovie.toLocalModel(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: "",
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage
)
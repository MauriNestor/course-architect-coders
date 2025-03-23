package org.architect_course.model.datasource

import kotlinx.coroutines.flow.Flow
import org.architect_course.model.database.Movie
import org.architect_course.model.database.MovieDao

class MovieLocalDataSource(private val movieDao: MovieDao) {

    val movies: Flow<List<Movie>> = movieDao.getAll()

    fun isEmpty(): Boolean = movieDao.movieCount() == 0

    fun save(movies: List<Movie>) {
        movieDao.insertMovies(movies)
    }
}
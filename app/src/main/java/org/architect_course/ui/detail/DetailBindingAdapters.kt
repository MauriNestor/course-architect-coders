package org.architect_course.ui.detail

import androidx.databinding.BindingAdapter
import org.architect_course.model.database.Movie


@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetails(movie: Movie?) {
    if (movie != null) {
        setMovie(movie)
    }
}
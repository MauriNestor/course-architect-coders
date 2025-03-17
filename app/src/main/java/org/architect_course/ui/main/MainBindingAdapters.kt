package org.architect_course.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import org.architect_course.model.Movie


@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<Movie>?) {
    if (movies != null) {
        (adapter as? MoviesAdapter)?.submitList(movies)
    }
}
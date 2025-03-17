package org.architect_course.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.architect_course.model.Movie
import org.architect_course.model.MoviesRepository

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        refresh()
    }
    private fun refresh(){
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(movies = moviesRepository.findPopularMovies().results)
        }
    }

    fun onMovieClicked(movie: Movie) {
        _state.value = _state.value.copy(navigateTo = movie)
    }

    fun onNavigateDone() {
        _state.value = _state.value.copy(navigateTo = null)
    }
    fun onLocationPermissionChecked() {
        _state.value = _state.value.copy(requestLocationPermission = false)
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val navigateTo: Movie? = null,
        val requestLocationPermission: Boolean = true
    )
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val moviesRepository: MoviesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository) as T
    }
}
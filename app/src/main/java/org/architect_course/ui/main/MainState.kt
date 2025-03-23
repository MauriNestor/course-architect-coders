package org.architect_course.ui.main

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.architect_course.model.database.Movie

import org.architect_course.ui.common.PermissionRequester

fun Fragment.buildMainState(
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = MainState(scope, navController, locationPermissionRequester)

class MainState(
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequester: PermissionRequester
) {
    fun onMovieClicked(movie: Movie) {
        val action = MainFragmentDirections.actionMainToDetail(movie.id)
        navController.navigate(action)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

}
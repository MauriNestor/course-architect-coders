package org.architect_course.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.architect_course.R
import org.architect_course.databinding.FragmentMainBinding
import org.architect_course.model.Movie
import org.architect_course.model.MoviesRepository
import org.architect_course.ui.common.PermissionRequester
import org.architect_course.ui.common.launchAndCollect
import org.architect_course.ui.common.visible


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MoviesRepository(requireActivity().application))
    }

    private val adapter = MoviesAdapter { viewModel.onMovieClicked(it) }
    private val coarsePermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding.updateUI(it) }
    }


    private fun FragmentMainBinding.updateUI(state: MainViewModel.UiState) {
        progress.visible = state.loading
        state.movies?.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
        if (state.requestLocationPermission) {
            requestLocationPermission()
        }
    }

    private fun navigateTo(movie: Movie) {
        val action = MainFragmentDirections.actionMainToDetail(movie)
        findNavController().navigate(action)
        viewModel.onNavigateDone()
    }
    private fun requestLocationPermission() {
        viewLifecycleOwner.lifecycleScope.launch {
            coarsePermissionRequester.request()
            viewModel.onLocationPermissionChecked()
        }
    }
}

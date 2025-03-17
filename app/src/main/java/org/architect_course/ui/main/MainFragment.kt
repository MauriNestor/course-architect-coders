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

    private lateinit var mainState : MainState

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding.updateUI(it) }
        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }


    private fun FragmentMainBinding.updateUI(state: MainViewModel.UiState) {
        progress.visible = state.loading
        state.movies?.let(adapter::submitList)
    }
}

package com.example.ucbposb

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.ucbposb.viewmodel.SharedViewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SharedViewModel by activityViewModels()
    private var isMinimumTimeReached = false
    private var isDataLoaded = false

    private fun navigateIfReady() {

        if (isMinimumTimeReached && isDataLoaded) {

            findNavController().navigate(
                R.id.action_splash_to_home,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.splashFragment, true)
                    .build()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        // Start API loading
        viewModel.loadHome()
        viewModel.loadMenu()

        // Keep splash for at least 3 seconds
        view?.postDelayed({

            isMinimumTimeReached = true

            navigateIfReady()

        }, 3000)
    }
    private fun observeData() {

        viewModel.homeLoaded.observe(viewLifecycleOwner) {
            checkDataLoaded()
        }

        viewModel.menuLoaded.observe(viewLifecycleOwner) {
            checkDataLoaded()
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->

            if (!error.isNullOrEmpty()) {
                showErrorDialog()
            }

        }
    }

    private fun checkDataLoaded() {

        val homeLoaded = viewModel.homeLoaded.value ?: false
        val menuLoaded = viewModel.menuLoaded.value ?: false

        if (homeLoaded && menuLoaded) {

            isDataLoaded = true

            navigateIfReady()

        }

    }

    private fun showErrorDialog() {

        AlertDialog.Builder(requireContext())
            .setTitle("Loading Failed")
            .setMessage("Unable to load data.\nPlease check your internet connection.")
            .setCancelable(false)
            .setPositiveButton("Retry") { _, _ ->

                viewModel.loadHome()
                viewModel.loadMenu()

            }
            .setNegativeButton("Exit") { _, _ ->

                requireActivity().finish()

            }
            .show()
    }
}
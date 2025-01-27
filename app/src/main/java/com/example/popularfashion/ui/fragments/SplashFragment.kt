package com.example.popularfashion.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.popularfashion.R
import com.example.popularfashion.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding
    private lateinit var activity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity=context as Activity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.apply {
            startBtn.setOnClickListener {
                navigateToDashboard()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }
    private fun navigateToDashboard() {
        val action = SplashFragmentDirections.actionSplashFragmentToDashboardFragment()
        navigateToDestination(action)
    }


    private fun navigateToDestination(action: NavDirections) {
        if (isAdded && isVisible) {
            try {
                if (findNavController().currentDestination?.id == R.id.splashFragment) {
                    view?.findNavController()?.navigate(action)
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
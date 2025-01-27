package com.example.popularfashion.ui.fragments.dashboard

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularfashion.R
import com.example.popularfashion.databinding.FragmentDashboardBinding
import com.example.popularfashion.di.NetworkModule
import com.example.popularfashion.repository.ProductRepository
import com.example.popularfashion.ui.fragments.dashboard.interfaces.FashionAction
import com.example.popularfashion.utils.Extension.gone
import com.example.popularfashion.utils.Extension.visible
import com.example.popularfashion.utils.NetworkResult
import com.example.popularfashion.utils.Utils.isNetworkConnected
import com.example.popularfashion.utils.ViewModelFactory
import com.example.popularfashion.viewModels.MainViewModel

class DashboardFragment : Fragment(),FashionAction {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding
    private var fashionAdapter:FashionAdapter?=null

    private val repository = ProductRepository(NetworkModule.apiInterface)



    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(repository)
    }
    private lateinit var activity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity=context as Activity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.appbarTitle?.text="Dashboard"
        setAdapter()
        if(isNetworkConnected(activity)) {
            mainViewModel.getAllProduct()
            bindObservers()
        } else Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show()




        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })


    }
    private fun bindObservers() {
        mainViewModel.fashionLiveData.observe(viewLifecycleOwner) {
            binding?.progress?.gone()
            when (it) {
                is NetworkResult.Success -> {
                    fashionAdapter?.submitList(it.data)
                }

                is NetworkResult.Error -> {
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                is NetworkResult.Loading -> {
                    binding?.progress?.visible()
                }
            }
        }
    }

    private fun setAdapter() {
        fashionAdapter = FashionAdapter(this@DashboardFragment)
        val layoutManager = LinearLayoutManager(activity)
        binding?.fashionItem?.layoutManager = layoutManager
        binding?.fashionItem?.adapter = fashionAdapter
    }
    private fun navigateToDetail(index: Int) {
        val action = DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(index)
        navigateToDestination(action)
    }


    private fun navigateToDestination(action: NavDirections) {
        if (isAdded && isVisible) {
            try {
                if (findNavController().currentDestination?.id == R.id.dashboardFragment) {
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

    override fun callToDetail(index: Int) {

     navigateToDetail(index)
    }
}
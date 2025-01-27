package com.example.popularfashion.ui.fragments.detail

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.popularfashion.R
import com.example.popularfashion.databinding.FragmentDetailBinding
import com.example.popularfashion.di.NetworkModule
import com.example.popularfashion.repository.ProductRepository
import com.example.popularfashion.utils.Extension.gone
import com.example.popularfashion.utils.Extension.visible
import com.example.popularfashion.utils.NetworkResult
import com.example.popularfashion.utils.Utils
import com.example.popularfashion.utils.ViewModelFactory
import com.example.popularfashion.viewModels.MainViewModel
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private val repository = ProductRepository(NetworkModule.apiInterface)

    private val args:DetailFragmentArgs by navArgs()

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(repository)
    }
    private lateinit var activity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.toolbar?.appbarTitle?.text="Detail"

        if(Utils.isNetworkConnected(activity)) {
            mainViewModel.getSingleProduct(args.index)

            bindObservers()
        } else Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show()




        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                popBackStack()
            }
        })
    }


    private fun bindObservers() {
        mainViewModel.detailLiveData.observe(viewLifecycleOwner) {
            binding?.progress?.gone()
            when (it) {
                is NetworkResult.Success -> {
                    val item = it.data?.get(args.index)

                    item?.let {


                        binding?.title?.text = item.title
                        binding?.detail?.text = item.description
                        binding?.offPercentage?.text = "${
                            "%.2f".format(
                                Utils.calculateOffPercentage(
                                    item.oldPrice.toDouble(),
                                    item.price
                                )
                            )
                        }% off"

                        binding?.price?.text = "RS.${item.price}"

                        binding?.oldPrice?.apply {
                            text = item.oldPrice // Set your text
                            paintFlags =
                                paintFlags or Paint.STRIKE_THRU_TEXT_FLAG // Add strike-through effect
                        }

                        binding?.rateStar?.text = item.rating.toString()
                        binding?.image?.let { it1 -> Utils.loadImage(item.image, it1) }
                    }
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


    private fun popBackStack() {
        if (isAdded && isVisible) {
            try {
                if (findNavController().currentDestination?.id == R.id.detailFragment) {
                    view?.findNavController()?.popBackStack()
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
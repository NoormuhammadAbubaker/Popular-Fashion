package com.example.popularfashion.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.widget.AppCompatImageView
import coil.load
import com.example.popularfashion.R

object Utils {
    fun calculateOffPercentage(oldPrice: Double, newPrice: Double): Double {
        if (oldPrice <= 0) throw IllegalArgumentException("Old price must be greater than 0")
        return ((oldPrice - newPrice) / oldPrice) * 100
    }
    fun loadImage(imageUrl: String, imageView: AppCompatImageView) {
        imageView.load(imageUrl) {
            placeholder(R.drawable.photo_place)
            error(R.drawable.photo_place)
        }
    }
    fun isNetworkConnected(context: Activity?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

}
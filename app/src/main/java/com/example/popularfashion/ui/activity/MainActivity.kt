package com.example.popularfashion.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.popularfashion.R
import com.example.popularfashion.di.NetworkModule

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        try {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
            navController = navHostFragment.navController
            navController.setGraph(R.navigation.navigation_route)


        } catch (ex: RuntimeException) {
            ex.stackTrace
        }
    }
}
package com.submission.dicodingstory.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.submission.dicodingstory.R
import com.submission.dicodingstory.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?)
    {super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment
        navController = navHost.navController
        binding.botNav.setupWithNavController(navController)



        navController.addOnDestinationChangedListener{_, dest, _ ->
            when(dest.id){
                R.id.dashboardFragment, R.id.optionFragment, R.id.googleMapsFragment
                -> binding.botNav.visibility = View.VISIBLE
                else -> binding.botNav.visibility = View.GONE}}
    }

    override fun onSupportNavigateUp(): Boolean {
        val dest = navController.currentDestination
        Timber.d("Nav Controller ${dest?.id} -> ${dest?.label}")
        return if (dest != null)
        {
            when(dest.label)
            {
                FRAGMENT_TO_DETAIL -> navController.navigateUp()
                else ->
                    navController.navigateUp()}}
        else navController.navigateUp()}


    companion object
    {
        const val FRAGMENT_TO_DETAIL = "fragment_to_detail"
    }
}
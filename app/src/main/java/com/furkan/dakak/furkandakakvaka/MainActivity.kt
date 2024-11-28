package com.furkan.dakak.furkandakakvaka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var sessionManager: SessionManager
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        manageBottomNavigationVisibility()

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.myCoursesFragment -> {
                    if (sessionManager.isLoggedIn()) {
                        navController.navigate(R.id.myCoursesFragment)
                    } else {
                        navController.navigate(R.id.loginFragment)
                    }
                    true
                }
                R.id.profileFragment -> {
                    if (sessionManager.isLoggedIn()) {
                        navController.navigate(R.id.profileFragment)
                    } else {
                        navController.navigate(R.id.loginFragment)
                    }
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (!sessionManager.isLoggedIn() && destination.id != R.id.loginFragment) {
                navController.navigate(R.id.loginFragment)
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.loginFragment) {
            super.onBackPressed()
        } else {
            // Ignore back press
        }
    }

    fun manageBottomNavigationVisibility() {
        if (sessionManager.isLoggedIn()) {
            bottomNavigationView.visibility = BottomNavigationView.VISIBLE
        } else {
            bottomNavigationView.visibility = BottomNavigationView.GONE
        }
    }
}

package com.example.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recipeapp.HomeFragment
import com.example.recipeapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Load the default fragment when the activity starts
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, HomeFragment())
                .commit()
        }

        // Set up BottomNavigationView to switch between fragments
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavBar)
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.home -> HomeFragment()
                R.id.heart-> FavouriteFragment()
//                R.id.navigation_favorites -> FavoritesFragment()
                // Add other cases for additional fragments
                else -> HomeFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, selectedFragment)
                .commit()

            true
        }

    }
}
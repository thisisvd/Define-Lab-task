package com.example.definelabtask.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.definelabtask.R
import com.example.definelabtask.constants.Constants.matchesMetaData
import com.example.definelabtask.databinding.ActivityMainBinding
import com.example.definelabtask.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    // view binding
    private lateinit var binding: ActivityMainBinding

    // nav controller
    private lateinit var navController: NavController

    // view model
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            // setup db
            viewModel.dbSetup(applicationContext)

            // Setup action bar with layout
            setSupportActionBar(toolbar)

            // Nav host fragment
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.navController
            setupActionBarWithNavController(navController)

            // view model observer
            viewModelObserver()

            // nav drawer
            navigationDrawer()
        }
    }

    // navigation drawer
    private fun navigationDrawer() {
        binding.apply {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity, drawerLayout, toolbar, 0, 0
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            mainNavView.setNavigationItemSelectedListener { item ->

                when (item.itemId) {
                    R.id.item_all_matches -> {
                        navController.navigate(R.id.action_defaultFragment_to_allMatchesFragment)
                        drawerLayout.closeDrawer(GravityCompat.START)
                        true
                    }

                    R.id.item_saved -> {
                        navController.navigate(R.id.action_defaultFragment_to_savedMatchesFragment)
                        drawerLayout.closeDrawer(GravityCompat.START)
                        true
                    }
                }
                true
            }
        }
    }

    // view model observers
    private fun viewModelObserver() {
        binding.apply {
            viewModel.apply {

                // match data observer
                viewModel.getMatchDataFromDB().observe(this@MainActivity) {
                    if (!it.isNullOrEmpty()) {
                        matchesMetaData.addAll(it)
                    } else {
                        matchesMetaData.clear()
                    }
                }
            }
        }
    }
}
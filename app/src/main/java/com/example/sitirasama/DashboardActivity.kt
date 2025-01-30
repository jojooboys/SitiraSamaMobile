package com.example.sitirasama

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sitirasama.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar sebagai ActionBar
        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_penitipan, R.id.navigation_barangditolak
            )
        )

        // Gunakan Toolbar sebagai ActionBar
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}

package com.example.sitirasama

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.sitirasama.databinding.ActivityDashboardBinding
import com.example.sitirasama.service.ApiClient

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ApiClient sebelum menggunakan API
        ApiClient.init(this)

        // Pastikan Toolbar sudah di-set sebagai ActionBar
        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.navView

        // Pastikan menggunakan `supportFragmentManager.findFragmentById()`
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_dashboard) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_pengajuan,
                R.id.navigation_penitipan,
                R.id.navigation_barangditolak,
                R.id.navigation_profil
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}

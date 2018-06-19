package com.jayway.example.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.jayway.example.github.navigation.NavigationUIX
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        // In lieu of NavigationUI.setupActionBarWithNavController(this, bottom_navigation) not being usable with androidx.app.appcompat.AppCompatActivity
        // replicate its functionality in the NavigationUIX
        NavigationUIX.setupActionBarWithNavController(this, host.navController)
        NavigationUIX.setupWithNavController(bottom_navigation, host.navController)

    }


    override fun onSupportNavigateUp() = NavigationUIX.navigateUp(null,
                                                                  findNavController(R.id.nav_host_fragment))
}

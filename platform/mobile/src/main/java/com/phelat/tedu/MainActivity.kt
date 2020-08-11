package com.phelat.tedu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.phelat.tedu.settings.entity.UserInterfaceMode
import com.phelat.tedu.navigation.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val userInterfaceMode = when (UserInterfaceMode.currentUserInterfaceMode) {
            is UserInterfaceMode.DarkMode -> AppCompatDelegate.MODE_NIGHT_YES
            is UserInterfaceMode.LightMode -> AppCompatDelegate.MODE_NIGHT_NO
            is UserInterfaceMode.Automatic -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(userInterfaceMode)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationView()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView.setupWithNavController(
            listOf(R.navigation.navigation_todolist, R.navigation.navigation_settings),
            supportFragmentManager,
            R.id.fragmentContainer,
            intent
        ).observe(this, Observer {})
    }
}
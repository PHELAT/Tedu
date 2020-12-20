package com.phelat.tedu

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.phelat.tedu.databinding.ActivityMainBinding
import com.phelat.tedu.navigation.setupWithNavController
import com.phelat.tedu.plaugin.ActivityPlugin
import com.phelat.tedu.plaugin.PlauginActivity
import com.phelat.tedu.plugins.invoke
import com.phelat.tedu.plugins.plugin
import com.phelat.tedu.plugins.viewBinding
import com.phelat.tedu.settings.entity.UserInterfaceMode

class MainActivity : PlauginActivity() {

    private val viewBinding = viewBinding {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val userInterfaceMode = when (UserInterfaceMode.currentUserInterfaceMode) {
            is UserInterfaceMode.DarkMode -> AppCompatDelegate.MODE_NIGHT_YES
            is UserInterfaceMode.LightMode -> AppCompatDelegate.MODE_NIGHT_NO
            is UserInterfaceMode.Automatic -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(userInterfaceMode)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            setupBottomNavigationView()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        viewBinding().bottomNavigationView.setupWithNavController(
            listOf(R.navigation.navigation_todolist, R.navigation.navigation_settings),
            supportFragmentManager,
            R.id.fragmentContainer,
            intent
        ).observe(this) {}
    }

    override fun plugins(): MutableList<ActivityPlugin> = mutableListOf(viewBinding.plugin)
}
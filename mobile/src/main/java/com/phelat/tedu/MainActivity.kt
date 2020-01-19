package com.phelat.tedu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.phelat.tedu.uiview.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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
            listOf(R.navigation.navigation_todolist),
            supportFragmentManager,
            R.id.fragmentContainer,
            intent
        ).observe(this, Observer {})
    }
}
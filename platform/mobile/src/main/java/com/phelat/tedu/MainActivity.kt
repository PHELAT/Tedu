package com.phelat.tedu

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.phelat.tedu.navigation.ExtraDataBag
import com.phelat.tedu.settings.entity.UserInterfaceMode
import com.phelat.tedu.navigation.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import java.io.Serializable

/**
    I think it's safer to implement `ExtraDataBag` using ViewModelSaveState but
    for the sake of example I think this is good enough
*/
class MainActivity : AppCompatActivity(), ExtraDataBag {

    private lateinit var bag: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        bag = savedInstanceState ?: Bundle()
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

    override fun onSaveInstanceState(outState: Bundle) {
        bag.putAll(outState)
        super.onSaveInstanceState(bag)
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

    override fun storeData(key: String, data: Parcelable) {
        bag.putParcelable(key, data)
    }

    override fun storeData(key: String, data: Serializable) {
        bag.putSerializable(key, data)
    }

    override fun <T : Parcelable> getParcel(key: String): T? {
        return bag.getParcelable(key)
    }

    override fun <T : Serializable> getSerial(key: String): T? {
        return bag.getSerializable(key) as? T
    }
}

package com.phelat.tedu.settings.view

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.settings.R
import com.phelat.tedu.settings.di.component.SettingsComponent
import com.phelat.tedu.settings.viewmodel.SettingsViewModel
import com.phelat.tedu.uiview.observeNavigation
import kotlinx.android.synthetic.main.fragment_settings.darkModeSwitch
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var settingsViewModelFactory: ViewModelProvider.Factory

    private val settingsViewModel: SettingsViewModel by viewModels { settingsViewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<SettingsComponent>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        darkModeSwitch.apply {
            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.font_size_regular)
            )
            typeface = ResourcesCompat.getFont(requireContext(), R.font.robotoslab_medium)
            setOnCheckedChangeListener { _, isChecked ->
                settingsViewModel.onDarkModeSwitchChange(isChecked)
            }
        }
        settingsViewModel.apply {
            darkModeSwitchObservable.observe(viewLifecycleOwner, darkModeSwitch::setChecked)
            navigationObservable.observeNavigation(this@SettingsFragment)
        }
    }
}
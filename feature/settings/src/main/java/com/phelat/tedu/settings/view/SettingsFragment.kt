package com.phelat.tedu.settings.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.designsystem.component.view.BottomSheetView
import com.phelat.tedu.designsystem.entity.BottomSheetEntity
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.navigation.observeNavigation
import com.phelat.tedu.settings.R
import com.phelat.tedu.settings.di.component.SettingsComponent
import com.phelat.tedu.settings.state.SettingsViewState
import com.phelat.tedu.settings.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.backupClick
import kotlinx.android.synthetic.main.fragment_settings.backupStatus
import kotlinx.android.synthetic.main.fragment_settings.backupStatusArrow
import kotlinx.android.synthetic.main.fragment_settings.contributorsClick
import kotlinx.android.synthetic.main.fragment_settings.userInterfaceMode
import kotlinx.android.synthetic.main.fragment_settings.userInterfaceModeClick
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val settingsViewModel: SettingsViewModel by viewModels { viewModelFactory }

    private var userInterfaceModeSheet: BottomSheetView? = null
    private var backupMethodSheet: BottomSheetView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<SettingsComponent>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userInterfaceModeClick.setOnClickListener {
            settingsViewModel.onChangeUserInterfaceModeClick()
        }
        backupClick.setOnClickListener {
            settingsViewModel.onBackUpClick()
        }
        contributorsClick.setOnClickListener {
            settingsViewModel.onContributorsClick()
        }
        settingsViewModel.apply {
            navigationObservable.observeNavigation(this@SettingsFragment)
            userInterfaceSheetObservable.observe(
                viewLifecycleOwner,
                ::observeUserInterfaceModeSheet
            )
            backupMethodSheetObservable.observe(
                viewLifecycleOwner,
                ::observeBackupMethodSheet
            )
            userInterfaceTitleObservable.observe(viewLifecycleOwner, userInterfaceMode::setText)
            viewStateObservable.observe(viewLifecycleOwner, ::observeViewState)
        }
    }

    private fun observeUserInterfaceModeSheet(entity: BottomSheetEntity) {
        userInterfaceModeSheet?.apply {
            sheetItems = entity.items
            sheetTitle = entity.sheetTitle
            show()
        } ?: run {
            userInterfaceModeSheet = BottomSheetView(requireContext())
            observeUserInterfaceModeSheet(entity)
        }
    }

    private fun observeBackupMethodSheet(entity: BottomSheetEntity) {
        backupMethodSheet?.apply {
            sheetItems = entity.items
            sheetTitle = entity.sheetTitle
            show()
        } ?: run {
            backupMethodSheet = BottomSheetView(requireContext())
            observeBackupMethodSheet(entity)
        }
    }

    private fun observeViewState(state: SettingsViewState) {
        state.apply {
            backupStatus.text = syncStateText
            backupStatus.isInvisible = isSyncStateTextVisible.not()
            backupStatusArrow.isVisible = isSyncArrowVisible
        }
    }

    override fun onDestroyView() {
        if (userInterfaceModeSheet?.isShowing == true) {
            userInterfaceModeSheet?.dismiss()
        }
        if (backupMethodSheet?.isShowing == true) {
            backupMethodSheet?.dismiss()
        }
        super.onDestroyView()
    }
}
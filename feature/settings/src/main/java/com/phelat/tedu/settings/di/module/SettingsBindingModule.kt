package com.phelat.tedu.settings.di.module

import com.phelat.tedu.settings.di.scope.SettingsScope
import com.phelat.tedu.settings.view.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface SettingsBindingModule {

    @SettingsScope
    @ContributesAndroidInjector(modules = [SettingsViewModule::class])
    fun bindSettingsFragment(): SettingsFragment
}
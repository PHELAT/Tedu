package com.phelat.tedu.settings.di.module

import com.phelat.tedu.settings.di.scope.SettingsScope
import com.phelat.tedu.settings.view.SettingsFragment
import com.phelat.tedu.settings.view.WebDavSetupFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface SettingsFragmentModule {

    @SettingsScope
    @ContributesAndroidInjector(modules = [SettingsViewModelModule::class])
    fun bindSettingsFragment(): SettingsFragment

    @SettingsScope
    @ContributesAndroidInjector(modules = [SettingsViewModelModule::class])
    fun bindWebDavSetupFragment(): WebDavSetupFragment
}
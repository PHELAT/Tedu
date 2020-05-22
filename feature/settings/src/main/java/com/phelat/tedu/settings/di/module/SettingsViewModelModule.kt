package com.phelat.tedu.settings.di.module

import androidx.lifecycle.ViewModel
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.lifecycle.ViewModelKey
import com.phelat.tedu.lifecycle.ViewModelProviders
import com.phelat.tedu.settings.di.scope.SettingsScope
import com.phelat.tedu.settings.viewmodel.SettingsViewModel
import com.phelat.tedu.settings.viewmodel.WebDavViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface SettingsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WebDavViewModel::class)
    fun bindWebDavViewModel(input: WebDavViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(input: SettingsViewModel): ViewModel

    companion object {
        @Provides
        @SettingsScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }
    }
}
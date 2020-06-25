package com.phelat.tedu.backup.di.module

import androidx.lifecycle.ViewModel
import com.phelat.tedu.backup.di.scope.BackupScope
import com.phelat.tedu.backup.viewmodel.WebDavViewModel
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.lifecycle.ViewModelKey
import com.phelat.tedu.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface BackupViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WebDavViewModel::class)
    @BackupScope
    fun bindWebDavViewModel(input: WebDavViewModel): ViewModel

    companion object {
        @Provides
        @BackupScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }
    }
}
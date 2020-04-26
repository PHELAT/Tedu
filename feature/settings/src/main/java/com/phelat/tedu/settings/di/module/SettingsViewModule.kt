package com.phelat.tedu.settings.di.module

import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.lifecycle.viewModelFactory
import com.phelat.tedu.settings.di.scope.SettingsScope
import com.phelat.tedu.settings.entity.UserInterfaceMode
import com.phelat.tedu.settings.viewmodel.SettingsViewModel
import dagger.Module
import dagger.Provides

@Module
class SettingsViewModule {

    @Provides
    @SettingsScope
    fun provideSettingsViewModelFactory(
        uiModeDataSourceReadable: Readable<UserInterfaceMode>,
        uiModeDataSourceWritable: Writable<UserInterfaceMode>,
        stringResourceProvider: ResourceProvider<StringId, StringResource>
    ) = viewModelFactory {
        SettingsViewModel(
            uiModeDataSourceReadable,
            uiModeDataSourceWritable,
            stringResourceProvider
        )
    }
}
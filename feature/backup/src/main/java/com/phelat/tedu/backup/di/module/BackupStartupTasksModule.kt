package com.phelat.tedu.backup.di.module

import com.phelat.tedu.backup.di.qualifier.BackupStartupTasks
import com.phelat.tedu.backup.usecase.WebDavBackupUseCase
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Module
class BackupStartupTasksModule {

    @Provides
    @FeatureScope
    @IntoMap
    @StringKey("WebDavSyncTask")
    @BackupStartupTasks
    fun provideWebDavSyncTask(usecase: WebDavBackupUseCase, dispatcher: Dispatcher) = Runnable {
        MainScope().launch(context = dispatcher.iO) {
            usecase.sync()
        }
    }
}
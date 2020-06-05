package com.phelat.tedu.backup.di.module

import com.phelat.tedu.backup.di.qualifier.BackupStartupTasks
import com.phelat.tedu.backup.usecase.BackupUseCase
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class BackupStartupTasksModule {

    @Provides
    @FeatureScope
    @IntoMap
    @StringKey("WebDavSyncTask")
    @BackupStartupTasks
    fun provideWebDavSyncTask(usecase: BackupUseCase) = Runnable {
        usecase.command()
    }
}
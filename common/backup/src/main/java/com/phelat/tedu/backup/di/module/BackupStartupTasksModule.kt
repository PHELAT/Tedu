package com.phelat.tedu.backup.di.module

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.di.qualifier.BackupStartupTasks
import com.phelat.tedu.dependencyinjection.common.CommonScope
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class BackupStartupTasksModule {

    @Provides
    @CommonScope
    @IntoMap
    @StringKey("WebDavSyncTask")
    @BackupStartupTasks
    fun provideWebDavSyncTask(repository: BackupSyncRepository) = Runnable {
        repository.sync()
    }
}
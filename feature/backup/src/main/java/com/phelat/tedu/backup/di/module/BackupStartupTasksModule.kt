package com.phelat.tedu.backup.di.module

import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.analytics.di.qualifier.Development
import com.phelat.tedu.backup.di.qualifier.BackupStartupTasks
import com.phelat.tedu.backup.usecase.BackupUseCase
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.ifNotSuccessful
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
    fun provideWebDavSyncTask(
        usecase: BackupUseCase,
        dispatcher: Dispatcher,
        @Development logger: ExceptionLogger
    ) = Runnable {
        MainScope().launch(context = dispatcher.iO) {
            usecase.sync(createIfNotExists = false)
                .ifNotSuccessful(logger::log)
        }
    }
}
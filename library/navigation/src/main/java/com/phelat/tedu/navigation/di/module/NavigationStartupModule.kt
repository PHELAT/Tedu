package com.phelat.tedu.navigation.di.module

import android.content.Context
import com.phelat.tedu.androidcore.di.qualifier.ApplicationContext
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import com.phelat.tedu.navigation.ExtraDataDataSource
import com.phelat.tedu.navigation.di.qualifier.NavigationStartupTasks
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class NavigationStartupModule {

    @Provides
    @LibraryScope
    @IntoMap
    @StringKey("ExtraDataDataSourceSetupTask")
    @NavigationStartupTasks
    fun provideExtraDataDataSourceSetupTask(@ApplicationContext context: Context) = Runnable {
        ExtraDataDataSource.setup(context)
    }
}
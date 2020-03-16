package com.phelat.tedu.androidcore.di.module

import android.app.Application
import android.content.Context
import com.phelat.tedu.androidcore.di.qualifier.ApplicationContext
import com.phelat.tedu.androidcore.di.scope.AndroidCoreScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    @AndroidCoreScope
    @ApplicationContext
    fun provideApplicationContext(application: Application): Context {
        return application
    }
}
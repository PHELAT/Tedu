package com.phelat.tedu.androidcore.di.component

import android.app.Application
import android.content.Context
import com.phelat.tedu.androidcore.di.module.ApplicationModule
import com.phelat.tedu.androidcore.di.qualifier.ApplicationContext
import com.phelat.tedu.androidcore.di.scope.AndroidCoreScope
import dagger.BindsInstance
import dagger.Component

@AndroidCoreScope
@Component(modules = [ApplicationModule::class])
interface AndroidCoreComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplication(application: Application): Builder
        fun build(): AndroidCoreComponent
    }

    fun exposeApplication(): Application

    @ApplicationContext
    fun exposeApplicationContext(): Context
}
package com.phelat.tedu.core.component

import android.content.Context
import com.phelat.tedu.core.expose.CoreComponentExpose
import com.phelat.tedu.core.module.ThreadModule
import com.phelat.tedu.core.qualifier.ApplicationContext
import com.phelat.tedu.core.scope.CoreScope
import dagger.BindsInstance
import dagger.Component

@CoreScope
@Component(modules = [ThreadModule::class])
interface CoreComponent : CoreComponentExpose {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplicationContext(@ApplicationContext context: Context): Builder
        fun build(): CoreComponent
    }
}
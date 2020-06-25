package com.phelat.tedu.designsystem.di.component

import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.designsystem.di.module.DesignSystemStartupModule
import com.phelat.tedu.designsystem.di.qualifier.DesignSystemStartupTask
import dagger.Component

@CommonScope
@Component(modules = [DesignSystemStartupModule::class])
interface DesignSystemComponent {

    @DesignSystemStartupTask
    fun startupTasks(): StartupTasks
}
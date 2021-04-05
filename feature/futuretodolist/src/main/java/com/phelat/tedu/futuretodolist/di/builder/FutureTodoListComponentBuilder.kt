package com.phelat.tedu.futuretodolist.di.builder

import com.phelat.tedu.analytics.di.builder.AnalyticsComponentBuilder
import com.phelat.tedu.androidresource.di.builder.AndroidResourceComponentBuilder
import com.phelat.tedu.coroutines.di.builder.ThreadComponentBuilder
import com.phelat.tedu.date.di.builder.DateComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.designsystem.di.builder.DesignSystemComponentBuilder
import com.phelat.tedu.futuretodolist.di.component.DaggerFutureTodoListComponent
import com.phelat.tedu.futuretodolist.di.component.FutureTodoListComponent
import com.phelat.tedu.todo.di.builder.TodoComponentBuilder

object FutureTodoListComponentBuilder : ComponentBuilder<FutureTodoListComponent>() {

    override fun initializeComponent(
        addStartupTask: (StartupTasks) -> Unit
    ): FutureTodoListComponent {
        return DaggerFutureTodoListComponent.builder()
            .threadComponent(ThreadComponentBuilder.getComponent(addStartupTask))
            .todoComponent(TodoComponentBuilder.getComponent(addStartupTask))
            .analyticsComponent(AnalyticsComponentBuilder.getComponent(addStartupTask))
            .dateComponent(DateComponentBuilder.getComponent(addStartupTask))
            .androidResourceComponent(AndroidResourceComponentBuilder.getComponent(addStartupTask))
            .designSystemComponent(DesignSystemComponentBuilder.getComponent(addStartupTask))
            .build()
    }
}
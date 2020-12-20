package com.phelat.tedu.futuretodolist.di.component

import com.phelat.tedu.analytics.di.component.AnalyticsComponent
import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.date.di.component.DateComponent
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.designsystem.di.component.DesignSystemComponent
import com.phelat.tedu.futuretodolist.di.module.FutureTodoListBindingModule
import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.Component
import dagger.android.AndroidInjectionModule

@FeatureScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        FutureTodoListBindingModule::class
    ],
    dependencies = [
        TodoComponent::class,
        ThreadComponent::class,
        AnalyticsComponent::class,
        DateComponent::class,
        AndroidResourceComponent::class,
        DesignSystemComponent::class
    ]
)
interface FutureTodoListComponent : DispatcherComponent
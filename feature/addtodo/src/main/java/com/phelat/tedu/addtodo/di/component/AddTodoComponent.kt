package com.phelat.tedu.addtodo.di.component

import com.phelat.tedu.addtodo.di.module.AddTodoBindingModule
import com.phelat.tedu.androiddagger.FeatureComponent
import com.phelat.tedu.androiddagger.FeatureModule
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.feature.HasCommonDependency
import com.phelat.tedu.dependencyinjection.scope.FeatureScope
import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.Component

@FeatureScope
@Component(
    modules = [FeatureModule::class, AddTodoBindingModule::class],
    dependencies = [TodoComponent::class, ThreadComponent::class, AndroidResourceComponent::class]
)
interface AddTodoComponent : FeatureComponent, HasCommonDependency
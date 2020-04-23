package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.todolist.di.qualifier.TodoListStartupTasks
import com.phelat.tedu.todolist.startup.DeleteArchivableTodosTask
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface TodoListStartupModule {

    @Binds
    @IntoMap
    @StringKey("DeleteArchivableTodosTask")
    @TodoListStartupTasks
    @FeatureScope
    fun bindDeleteArchivableTodosTask(input: DeleteArchivableTodosTask): Runnable
}
package com.phelat.tedu.addtodo.di.module

import com.phelat.tedu.addtodo.di.scope.AddTodoSubScope
import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.lifecycle.viewModelFactory
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.Module
import dagger.Provides
import org.threeten.bp.LocalDate
import java.util.Date

@Module
class AddTodoModule {

    @Provides
    @AddTodoSubScope
    fun provideAddTodoViewModelFactory(
        dispatcher: Dispatcher,
        todoDataSourceWritable: Writable.Suspendable<TodoEntity>,
        todoDataSourceUpdatable: Updatable.Suspendable<TodoEntity>,
        stringResourceProvider: ResourceProvider<StringId, StringResource>,
        dateToLocalDate: Mapper<Date, LocalDate>
    ) = viewModelFactory {
        AddTodoViewModel(
            dispatcher,
            todoDataSourceWritable,
            todoDataSourceUpdatable,
            stringResourceProvider,
            dateToLocalDate
        )
    }
}
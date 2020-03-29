package com.phelat.tedu.addtodo.di.module

import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.functional.Response
import com.phelat.tedu.lifecycle.viewModelFactory
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import dagger.Module
import dagger.Provides
import org.threeten.bp.LocalDate
import java.util.Date

@Module
class AddTodoModule {

    @Provides
    @AddTodoScope
    fun provideAddTodoViewModelFactory(
        dispatcher: Dispatcher,
        todoDataSourceWritable: Writable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
        todoDataSourceUpdatable: Updatable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
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
package com.phelat.tedu.todo.di.module

import com.phelat.tedu.dependencyinjection.scope.CommonScope
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.mapper.DateToLocalDate
import com.phelat.tedu.todo.mapper.TodoDatabaseEntityToTodoEntity
import dagger.Binds
import dagger.Module
import org.threeten.bp.LocalDate
import java.util.Date

@Module
internal interface TodoMapperModule {

    @Binds
    @CommonScope
    fun bindTodoDatabaseEntityToTodoEntity(
        input: TodoDatabaseEntityToTodoEntity
    ): Mapper<TodoDatabaseEntity, TodoEntity>

    @Binds
    @CommonScope
    fun bindDateToLocalDate(input: DateToLocalDate): Mapper<Date, LocalDate>
}
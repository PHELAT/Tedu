package com.phelat.tedu.todo.di.module

import android.content.Context
import androidx.room.Room
import com.phelat.tedu.todo.database.TodoDatabase
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.di.scope.TodoScope
import dagger.Module
import dagger.Provides

@Module
internal class TodoDatabaseModule {

    @TodoScope
    @Provides
    fun provideTodoDatabase(context: Context): TodoDatabase  {
        return Room.databaseBuilder(context, TodoDatabase::class.java, "todo_database")
            .build()
    }

    @TodoScope
    @Provides
    fun provideTodoEntityDao(todoDatabase: TodoDatabase): TodoEntityDao {
        return todoDatabase.todoEntityDao()
    }
}
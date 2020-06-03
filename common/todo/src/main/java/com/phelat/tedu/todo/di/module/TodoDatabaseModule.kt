package com.phelat.tedu.todo.di.module

import android.content.Context
import androidx.room.Room
import com.phelat.tedu.androidcore.di.qualifier.ApplicationContext
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.todo.database.TodoDatabase
import com.phelat.tedu.todo.database.dao.ActionEntityDao
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import dagger.Module
import dagger.Provides

@Module
internal class TodoDatabaseModule {

    @Provides
    @CommonScope
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, "todo_database")
            .build()
    }

    @Provides
    @CommonScope
    fun provideTodoEntityDao(todoDatabase: TodoDatabase): TodoEntityDao {
        return todoDatabase.todoEntityDao()
    }

    @Provides
    @CommonScope
    fun provideActionEntityDao(todoDatabase: TodoDatabase): ActionEntityDao {
        return todoDatabase.actionEntityDao()
    }
}
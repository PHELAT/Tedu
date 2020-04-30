package com.phelat.tedu.addtodo.di.module

import com.phelat.tedu.addtodo.datasource.SelectedDateDataSource
import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.addtodo.entity.SelectedDate
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import dagger.Binds
import dagger.Module

@Module
interface SelectedDateDataSourceModule {

    @Binds
    @AddTodoScope
    fun bindSelectedDataSourceReadable(input: SelectedDateDataSource): Readable<SelectedDate>

    @Binds
    @AddTodoScope
    fun bindSelectedDataSourceWritable(input: SelectedDateDataSource): Writable<SelectedDate>
}
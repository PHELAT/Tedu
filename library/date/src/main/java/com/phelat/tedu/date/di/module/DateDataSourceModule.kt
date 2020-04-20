package com.phelat.tedu.date.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.date.datasource.DateDataSource
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import dagger.Binds
import dagger.Module
import java.util.Date

@Module
interface DateDataSourceModule {

    @Binds
    @LibraryScope
    fun bindDateDataSource(input: DateDataSource): Readable.IO<TeduDate, Date>
}
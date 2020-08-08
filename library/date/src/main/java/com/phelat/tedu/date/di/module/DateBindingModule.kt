package com.phelat.tedu.date.di.module

import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.date.datasource.DateDataSource
import com.phelat.tedu.date.datasource.DateDataSourceReadable
import com.phelat.tedu.date.datasource.LocalDateToTeduDate
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import com.phelat.tedu.mapper.Mapper
import dagger.Binds
import dagger.Module
import org.threeten.bp.LocalDate

@Module
internal interface DateBindingModule {

    @Binds
    @LibraryScope
    fun bindDateDataSource(input: DateDataSource): DateDataSourceReadable

    @Binds
    @LibraryScope
    fun bindLocalDateToTeduDate(input: LocalDateToTeduDate): Mapper<LocalDate, TeduDate>
}
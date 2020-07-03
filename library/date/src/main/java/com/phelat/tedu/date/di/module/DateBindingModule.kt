package com.phelat.tedu.date.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.date.datasource.DateDataSource
import com.phelat.tedu.date.datasource.LocalDateToTeduDate
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import com.phelat.tedu.mapper.Mapper
import dagger.Binds
import dagger.Module
import org.threeten.bp.LocalDate
import java.util.Date

@Module
interface DateBindingModule {

    @Binds
    @LibraryScope
    fun bindDateDataSource(input: DateDataSource): Readable.IO<TeduDate, Date>

    @Binds
    @LibraryScope
    fun bindLocalDateToTeduDate(input: LocalDateToTeduDate): Mapper<LocalDate, TeduDate>
}
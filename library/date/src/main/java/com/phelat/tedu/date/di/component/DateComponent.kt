package com.phelat.tedu.date.di.component

import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.date.datasource.DateDataSourceReadable
import com.phelat.tedu.date.di.module.DateBindingModule
import com.phelat.tedu.date.di.module.DateModule
import com.phelat.tedu.date.di.qualifier.NowDate
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import com.phelat.tedu.mapper.Mapper
import dagger.Component
import org.threeten.bp.LocalDate

@LibraryScope
@Component(modules = [DateModule::class, DateBindingModule::class])
interface DateComponent {

    fun exposeDateDataSource(): DateDataSourceReadable

    fun exposeLocalDateToTeduDate(): Mapper<LocalDate, TeduDate>

    @NowDate
    fun exposeNowDate(): Lazy<LocalDate>
}
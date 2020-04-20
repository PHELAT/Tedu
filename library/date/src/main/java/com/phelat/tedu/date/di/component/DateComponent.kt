package com.phelat.tedu.date.di.component

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.date.di.module.DateDataSourceModule
import com.phelat.tedu.date.di.module.DateModule
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import dagger.Component
import java.util.Date

@LibraryScope
@Component(modules = [DateModule::class, DateDataSourceModule::class])
interface DateComponent {

    fun exposeDateDataSource(): Readable.IO<TeduDate, Date>
}
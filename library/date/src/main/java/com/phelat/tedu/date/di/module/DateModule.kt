package com.phelat.tedu.date.di.module

import com.phelat.tedu.dependencyinjection.library.LibraryScope
import dagger.Module
import dagger.Provides
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId

@Module
class DateModule {

    @Provides
    @LibraryScope
    fun provideZoneId(): ZoneId {
        return ZoneId.systemDefault()
    }

    @Provides
    @LibraryScope
    fun provideNowLocalDate(): LocalDate {
        return LocalDate.now()
    }
}
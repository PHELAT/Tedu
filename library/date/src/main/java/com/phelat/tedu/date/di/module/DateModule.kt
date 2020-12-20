package com.phelat.tedu.date.di.module

import com.phelat.tedu.date.di.qualifier.NowDate
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import dagger.Module
import dagger.Provides
import java.time.LocalDate
import java.time.ZoneId

@Module
class DateModule {

    @Provides
    @LibraryScope
    fun provideZoneId(): Lazy<ZoneId> = lazy {
        ZoneId.systemDefault()
    }

    @Provides
    @LibraryScope
    @NowDate
    fun provideNowLocalDate(): Lazy<LocalDate> = lazy {
        LocalDate.now()
    }
}
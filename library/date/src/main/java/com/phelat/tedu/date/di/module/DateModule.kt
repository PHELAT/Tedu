package com.phelat.tedu.date.di.module

import com.phelat.tedu.date.cachedValue
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import dagger.Module
import dagger.Provides
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId

@Module
class DateModule {

    @Provides
    @LibraryScope
    fun provideZoneId(): () -> ZoneId = cachedValue {
        ZoneId.systemDefault()
    }

    @Provides
    @LibraryScope
    fun provideNowLocalDate(): () -> LocalDate = cachedValue {
        LocalDate.now()
    }
}
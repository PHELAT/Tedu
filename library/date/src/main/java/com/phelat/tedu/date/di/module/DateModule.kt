package com.phelat.tedu.date.di.module

import com.phelat.tedu.date.di.qualifier.NowDate
import dagger.Module
import dagger.Provides
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId

@Module
class DateModule {

    @Provides
    fun provideZoneId(): Lazy<ZoneId> = lazy {
        ZoneId.systemDefault()
    }

    @Provides
    @NowDate
    fun provideNowLocalDate(): Lazy<LocalDate> = lazy {
        LocalDate.now()
    }
}
package com.phelat.tedu.settings.di.module

import android.content.Context
import android.content.SharedPreferences
import com.phelat.tedu.androidcore.di.qualifier.ApplicationContext
import com.phelat.tedu.settings.di.scope.SettingsScope
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun provideSettingsSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("settings_preferences", Context.MODE_PRIVATE)
    }
}
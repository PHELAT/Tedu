package com.phelat.tedu.settings.di.module

import android.content.Context
import android.content.SharedPreferences
import com.phelat.tedu.androidcore.di.qualifier.ApplicationContext
import com.phelat.tedu.dependencyinjection.scope.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    @FeatureScope
    fun provideSettingsSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("settings_preferences", Context.MODE_PRIVATE)
    }
}
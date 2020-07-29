package com.phelat.tedu.backup.di.module

import android.content.Context
import android.content.SharedPreferences
import com.phelat.tedu.androidcore.di.qualifier.ApplicationContext
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.thegrizzlylabs.sardineandroid.Sardine
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
internal class WebDavModule {

    @FeatureScope
    @Provides
    fun provideSardine(okHttpClient: OkHttpClient): Sardine {
        return OkHttpSardine(okHttpClient)
    }

    @FeatureScope
    @Provides
    fun provideWebDavPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(WEB_DAV_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    companion object {
        private const val WEB_DAV_SHARED_PREFERENCES = "tedu_webdav"
    }
}
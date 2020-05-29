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
import java.util.concurrent.TimeUnit

@Module
internal class WebDavModule {

    @FeatureScope
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(CALL_TIME_OUT_IN_MILLIS, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIME_OUT_IN_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIME_OUT_IN_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIME_OUT_IN_MILLIS, TimeUnit.MILLISECONDS)
            .build()
    }

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
        private const val CALL_TIME_OUT_IN_MILLIS = 15000L
        private const val CONNECT_TIME_OUT_IN_MILLIS = 10000L
        private const val READ_TIME_OUT_IN_MILLIS = 30000L
        private const val WRITE_TIME_OUT_IN_MILLIS = 30000L
        private const val WEB_DAV_SHARED_PREFERENCES = "tedu_webdav"
    }
}
package com.phelat.tedu.networking.di.module

import com.phelat.tedu.dependencyinjection.library.LibraryScope
import com.phelat.tedu.networking.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
internal class NetworkModule {

    @Provides
    @LibraryScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @LibraryScope
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(CALL_TIME_OUT_IN_MILLIS, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIME_OUT_IN_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIME_OUT_IN_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIME_OUT_IN_MILLIS, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    companion object {
        private const val CALL_TIME_OUT_IN_MILLIS = 15000L
        private const val CONNECT_TIME_OUT_IN_MILLIS = 10000L
        private const val READ_TIME_OUT_IN_MILLIS = 30000L
        private const val WRITE_TIME_OUT_IN_MILLIS = 30000L
    }
}
package com.phelat.tedu.contributors.di.module

import com.phelat.tedu.contributors.api.ContributorsAPI
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
class ContributorsNetworkModule {

    @Provides
    @ContributorsScope
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @ContributorsScope
    fun provideContributorsAPI(retrofit: Retrofit): ContributorsAPI {
        return retrofit.create()
    }

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}
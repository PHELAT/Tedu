package com.phelat.tedu.networking.di.component

import com.phelat.tedu.dependencyinjection.library.LibraryScope
import com.phelat.tedu.networking.di.module.NetworkModule
import dagger.Component
import okhttp3.OkHttpClient

@LibraryScope
@Component(modules = [NetworkModule::class])
interface NetworkingComponent {

    fun exposeOkHttpClient(): OkHttpClient
}
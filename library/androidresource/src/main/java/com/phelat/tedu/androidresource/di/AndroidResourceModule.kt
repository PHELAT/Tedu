package com.phelat.tedu.androidresource.di

import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.provider.StringIdToStringResource
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.dependencyinjection.ModuleContainer
import org.koin.dsl.bind
import org.koin.dsl.module

object AndroidResourceModule : ModuleContainer {

    override fun getModule() = module {
        factory {
            StringIdToStringResource(context = get())
        }.bind<ResourceProvider<StringId, StringResource>>()
    }
}
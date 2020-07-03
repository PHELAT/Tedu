package com.phelat.tedu.androidresource.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.di.module.AndroidResourceModule
import com.phelat.tedu.androidresource.input.StringArg
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import dagger.Component

@LibraryScope
@Component(modules = [AndroidResourceModule::class], dependencies = [AndroidCoreComponent::class])
interface AndroidResourceComponent {

    fun exposeStringIdToStringResource(): ResourceProvider<StringId, StringResource>

    fun exposeStringArgToStringResource(): ResourceProvider<StringArg, StringResource>
}
package com.phelat.tedu.androidresource

interface ResourceProvider<InputType : Input, ResourceType : Resource> {

    fun getResource(input: InputType): ResourceType
}
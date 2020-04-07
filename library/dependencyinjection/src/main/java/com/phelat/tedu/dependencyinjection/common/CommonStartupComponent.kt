package com.phelat.tedu.dependencyinjection.common

interface CommonStartupComponent {

    @CommonStartupTasks
    fun commonStartUpTasks(): MutableMap<String, Runnable>
}
package com.phelat.tedu.dependencyinjection.feature

import com.phelat.tedu.dependencyinjection.common.CommonStartupTasks

interface HasCommonDependency {

    @CommonStartupTasks
    fun commonStartUpTasks(): MutableMap<String, Runnable>
}
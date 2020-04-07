package com.phelat.tedu.dependencyinjection.feature

interface FeatureStartupComponent {

    @FeatureStartupTasks
    fun featureStartUpTasks(): MutableMap<String, Runnable>
}
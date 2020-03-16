package com.phelat.tedu.dependencyinjection

abstract class ComponentBuilder<Component> {

    private var component: Component? = null

    fun getComponent(): Component {
        if (component == null) {
            synchronized(this) {
                if (component == null) {
                    component = initializeComponent()
                }
            }
        }
        return component!!
    }

    protected abstract fun initializeComponent(): Component
}
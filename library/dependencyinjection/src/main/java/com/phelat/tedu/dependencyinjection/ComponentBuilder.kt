package com.phelat.tedu.dependencyinjection

abstract class ComponentBuilder<Component : Any> {

    private var component: Component? = null

    fun getComponent(addStartupTask: (StartupTasks) -> Unit): Component {
        if (component == null) {
            synchronized(this) {
                if (component == null) {
                    component = initializeComponent(addStartupTask)
                    val startupTasks = getStartupTasks(requireNotNull(component))
                    if (startupTasks != null && startupTasks.isNotEmpty()) {
                        addStartupTask.invoke(startupTasks)
                    }
                }
            }
        }
        return component!!
    }

    protected open fun getStartupTasks(component: Component): StartupTasks? = null

    protected abstract fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): Component
}
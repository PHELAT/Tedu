package com.phelat.tedu.core.builder

import android.content.Context
import com.phelat.tedu.core.component.CoreComponent
import com.phelat.tedu.core.component.DaggerCoreComponent

object CoreComponentBuilder {

    @Volatile
    private var component: CoreComponent? = null

    fun getComponent(context: Context): CoreComponent {
        if (component == null) {
            synchronized(this) {
                if (component == null) {
                    component = DaggerCoreComponent.builder()
                        .bindApplicationContext(context)
                        .build()
                }
            }
        }
        return requireNotNull(component)
    }
}
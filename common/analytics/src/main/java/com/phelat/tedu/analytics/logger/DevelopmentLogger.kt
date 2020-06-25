package com.phelat.tedu.analytics.logger

import com.phelat.tedu.analytics.BuildConfig
import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.dependencyinjection.common.CommonScope
import javax.inject.Inject

@CommonScope
class DevelopmentLogger @Inject constructor() : ExceptionLogger {

    override fun log(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
    }
}
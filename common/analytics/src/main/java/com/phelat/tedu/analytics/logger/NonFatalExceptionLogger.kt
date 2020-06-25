package com.phelat.tedu.analytics.logger

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.phelat.tedu.analytics.BuildConfig
import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.dependencyinjection.common.CommonScope
import javax.inject.Inject

@CommonScope
class NonFatalExceptionLogger @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) : ExceptionLogger {

    override fun log(throwable: Throwable) {
        crashlytics.recordException(throwable)
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
    }
}
package com.phelat.tedu.analytics.di.module

import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.analytics.di.qualifier.Development
import com.phelat.tedu.analytics.di.qualifier.NonFatal
import com.phelat.tedu.analytics.logger.DevelopmentLogger
import com.phelat.tedu.analytics.logger.NonFatalExceptionLogger
import com.phelat.tedu.dependencyinjection.common.CommonScope
import dagger.Binds
import dagger.Module

@Module
interface LoggerModule {

    @Binds
    @CommonScope
    @NonFatal
    fun bindNonFatalExceptionLogger(input: NonFatalExceptionLogger): ExceptionLogger

    @Binds
    @CommonScope
    @Development
    fun bindNonDevelopmentExceptionLogger(input: DevelopmentLogger): ExceptionLogger
}
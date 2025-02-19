/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.build.gradle.internal

import com.android.utils.ILogger
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import java.io.Serializable
import java.util.function.Supplier

/**
 * Implementation of Android's [ILogger] over Gradle's [Logger].
 *
 * Note that this maps info to the default user-visible lifecycle.
 */
@Suppress("UNCHECKED_CAST")
class LoggerWrapper(private val logger: Logger) : ILogger {

    object Switch {

        @JvmField
        var shouldShowInfoLogsAsLifecycle = false
    }

    override fun error(throwable: Throwable?, s: String?, vararg objects: Any) {
        var message = s
        if (throwable != null && throwable::class.java.simpleName.contains("MergingException")) {
            // MergingExceptions have a known cause: they aren't internal errors, they
            // are errors in the user's code, so a full exception is not helpful (and
            // these exceptions should include a pointer to the user's error right in
            // the message).
            //
            // Furthermore, these exceptions are already caught by the MergeResources
            // and MergeAsset tasks, so don't duplicate the output
            return
        }
        if (!logger.isEnabled(ILOGGER_ERROR)) {
            return
        }
        if (message == null) {
            message = "[no message defined]"
        } else if (objects.isNotEmpty()) {
            message = String.format(message, *objects)
        }
        if (throwable == null) {
            logger.log(ILOGGER_ERROR, message)
        } else {
            logger.log(ILOGGER_ERROR, message, throwable)
        }
    }

    override fun warning(s: String, vararg objects: Any) {
        log(ILOGGER_WARNING, s, objects as Array<Any>?)
    }

    override fun quiet(s: String, vararg objects: Any) {
        log(ILOGGER_QUIET, s, objects as Array<Any>?)
    }

    override fun lifecycle(s: String, vararg objects: Any) {
        log(ILOGGER_LIFECYCLE, s, objects as Array<Any>?)
    }

    override fun info(s: String, vararg objects: Any) {
        log(ILOGGER_INFO, s, objects as Array<Any>?)
    }

    override fun verbose(s: String, vararg objects: Any) {
        log(ILOGGER_VERBOSE, s, objects as Array<Any>?)
    }

    private fun log(logLevel: LogLevel, s: String, objects: Array<out Any>?) {
        var newLogLevel = logLevel
        if (Switch.shouldShowInfoLogsAsLifecycle && newLogLevel == LogLevel.INFO) {
            newLogLevel = LogLevel.LIFECYCLE
        } else {
            if (!logger.isEnabled(newLogLevel)) {
                return
            }
        }
        if (objects == null || objects.isEmpty()) {
            logger.log(newLogLevel, s)
        } else {
            logger.log(logLevel, String.format(s, *objects))
        }
    }

    private class LoggerSupplier(private val clazz: Class<*>) : Supplier<ILogger>, Serializable {

        private var logger: ILogger? = null

        @Synchronized
        override fun get(): ILogger {
            if (logger == null) {
                logger = LoggerWrapper(Logging.getLogger(clazz))
            }

            return logger!!
        }
    }

    companion object {

        // Mapping from ILogger method call to gradle log level.
        private val ILOGGER_ERROR = LogLevel.ERROR
        private val ILOGGER_WARNING = LogLevel.WARN
        private val ILOGGER_QUIET = LogLevel.QUIET
        private val ILOGGER_LIFECYCLE = LogLevel.LIFECYCLE
        private val ILOGGER_INFO = LogLevel.INFO
        private val ILOGGER_VERBOSE = LogLevel.INFO

        @JvmStatic
        fun getLogger(klass: Class<*>): LoggerWrapper {
            return LoggerWrapper(Logging.getLogger(klass))
        }

        /**
         * Return a [Supplier] for an instance of [ILogger] for the given class c.
         *
         * @param c the class' used to provide a logger name
         * @return the [Supplier] for a logger instance.
         */
        @JvmStatic
        fun supplierFor(c: Class<*>): Supplier<ILogger> {
            return LoggerSupplier(c)
        }
    }
}

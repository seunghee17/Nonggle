package com.capstone.nongglenonggle.core.common.logger

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

object AppLogger {
    @Volatile
    private var crashlytics: FirebaseCrashlytics? = null
    @Volatile
    private var analytics: FirebaseAnalytics? = null

    fun init(crashlytics: FirebaseCrashlytics?, analytics: FirebaseAnalytics?) {
        this.crashlytics = crashlytics
        this.analytics = analytics
    }

    fun d(message: String) = Timber.d(message)

    fun i(message: String) = Timber.i(message)

    fun e(message: String, throwable: Throwable? = null) {
        Timber.e(throwable, message)
        val exception = throwable ?: Exception(message).apply {
            stackTrace = Thread.currentThread().stackTrace.drop(1).toTypedArray()
        }
        crashlytics?.recordException(exception)

    }

    fun event(name: String, params: Bundle? = null) {
        analytics?.logEvent(name, params)
    }
}

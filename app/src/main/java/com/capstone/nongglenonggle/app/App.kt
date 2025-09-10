package com.capstone.nongglenonggle.app

import android.app.Application
import com.capstone.nongglenonggle.core.common.logger.AppLogger
import com.capstone.nongglenonggle.core.common.logger.ReleaseTree
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import io.grpc.android.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val crashlytics = FirebaseCrashlytics.getInstance()
        AppLogger.init(
            crashlytics = crashlytics,
            analytics = Firebase.analytics
        )

        if(BuildConfig.DEBUG) {
            Timber.plant(object: Timber.DebugTree(){
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                        "Class:%s: Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        } else {
            Timber.plant(ReleaseTree(crashlytics = crashlytics))
        }
    }
}
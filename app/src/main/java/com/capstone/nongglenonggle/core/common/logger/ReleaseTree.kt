package com.capstone.nongglenonggle.core.common.logger

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.jetbrains.annotations.NotNull
import timber.log.Timber

class ReleaseTree(private val crashlytics: FirebaseCrashlytics) : @NotNull Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR) {

            // Crashlytics에 일반 로그로 남기기
            crashlytics.log("${tag.orEmpty()}: $message")

            // 실제 예외가 있으면 기록
            if (t != null) {
                crashlytics.recordException(t)
            }
        }
    }
}
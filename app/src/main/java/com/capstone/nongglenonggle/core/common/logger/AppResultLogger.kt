package com.capstone.nongglenonggle.core.common.logger

import com.capstone.nongglenonggle.data.network.AppResult

object AppResultLogger {
    fun logFailure(tag: String, failure: AppResult.Failure) {
        val caller = Throwable().stackTrace
            .firstOrNull { !it.className.contains("AppResultLogger") }
        val tag =
            "${caller?.className ?: "Unknown class"}#${caller?.methodName ?: "Unknown method"}"

        when (failure) {
            is AppResult.Failure.NetworkError -> {
                AppLogger.e("$tag: 네트워크 오류 발생", failure.throwable)
            }

            is AppResult.Failure.PermissionDenied -> {
                AppLogger.e("$tag: 권한 오류 발생", failure.throwable)
            }

            is AppResult.Failure.NotFound -> {
                AppLogger.e("$tag: 데이터 없음 오류 발생", failure.throwable)
            }

            is AppResult.Failure.Unknown -> {
                AppLogger.e("$tag: 알 수 없는 오류 발생", failure.throwable)
            }
        }
    }
}

inline fun <reified T> AppResultLogger.logFailure(
    failure: AppResult.Failure
) {
    val tag = T::class.java.simpleName
    when (failure) {
        is AppResult.Failure.NetworkError ->
            AppLogger.e("$tag - 네트워크 오류", failure.throwable)

        is AppResult.Failure.PermissionDenied ->
            AppLogger.e("$tag - 권한 오류", failure.throwable)

        is AppResult.Failure.NotFound ->
            AppLogger.e("$tag - 데이터 없음", failure.throwable)

        is AppResult.Failure.Unknown ->
            AppLogger.e("$tag - 알 수 없는 오류", failure.throwable)
    }
}
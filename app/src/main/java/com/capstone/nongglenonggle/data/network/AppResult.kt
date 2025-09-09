package com.capstone.nongglenonggle.data.network

// Data Layer 응답 (SDK/네트워크 에러 그대로 매핑)
sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    sealed interface Failure : AppResult<Nothing> {
        data class NetworkError(val throwable: Throwable) : Failure
        data class PermissionDenied(val throwable: Throwable) : Failure
        data class NotFound(val throwable: Throwable) : Failure
        data class Unknown(val throwable: Throwable) : Failure

        fun asThrowable(): Throwable = when (this) {
            is NetworkError -> throwable
            is PermissionDenied -> throwable
            is NotFound -> throwable
            is Unknown -> throwable
        }
    }
}

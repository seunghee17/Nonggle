package com.capstone.nongglenonggle.data.network

//- local, remote 데이터 소스 호출결과를 일관성 있게 표현하기 위한 wrapper class 입니다
//- 성공시 AppResult.Success(data) 형태로 viewmodel에서 ui로 전달됩니다
//- 실패시 에러의 원인을 카테고리화 해서 전달한다
//- ViewModel/UI에서 **한 가지 방식으로 에러 처리** 가능.
sealed interface AppResult<out T> {
    // 성공 응답
    data class Success<T>(val data: T) : AppResult<T>

    // 실패 응답
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

    companion object {
        // Success 생성 헬퍼
        fun <T> success(data: T): AppResult<T> = Success(data)

        // Failure 생성 헬퍼
        fun networkError(t: Throwable): AppResult<Nothing> = Failure.NetworkError(t)
        fun permissionDenied(t: Throwable): AppResult<Nothing> = Failure.PermissionDenied(t)
        fun notFound(t: Throwable): AppResult<Nothing> = Failure.NotFound(t)
        fun unknown(t: Throwable): AppResult<Nothing> = Failure.Unknown(t)
    }
}

// 확장 함수
inline fun <T> AppResult<T>.onSuccess(action: (T) -> Unit): AppResult<T> {
    if (this is AppResult.Success) action(data)
    return this
}

inline fun <T> AppResult<T>.onFailure(action: (AppResult.Failure) -> Unit): AppResult<T> {
    if (this is AppResult.Failure) action(this)
    return this
}

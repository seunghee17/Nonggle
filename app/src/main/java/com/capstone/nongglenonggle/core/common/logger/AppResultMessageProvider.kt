package com.capstone.nongglenonggle.core.common.logger

import com.capstone.nongglenonggle.data.network.AppResult

object AppResultMessageProvider {
    fun message(failure: AppResult.Failure): String = when (failure) {
        is AppResult.Failure.NetworkError -> "네트워크 연결을 확인해 주세요."
        is AppResult.Failure.PermissionDenied -> "권한이 없습니다."
        is AppResult.Failure.NotFound -> "요청하신 데이터를 찾을 수 없습니다."
        is AppResult.Failure.Internal -> "시스템 오류가 발생했습니다.\n잠시 후 다시 시도해주세요."
        else -> "알 수 없는 오류가 발생했습니다. 다시 시도해주세요."
    }
}

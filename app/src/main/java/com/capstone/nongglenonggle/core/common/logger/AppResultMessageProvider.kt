package com.capstone.nongglenonggle.core.common.logger

import android.content.Context
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.data.AppResult

object AppResultMessageProvider {
    fun message(context: Context, failure: AppResult.Failure): String = when (failure) {
        is AppResult.Failure.NetworkError -> context.getString(R.string.check_network_connect)
        is AppResult.Failure.PermissionDenied -> context.getString(R.string.check_permission_denied)
        is AppResult.Failure.NotFound -> context.getString(R.string.check_not_found)
        is AppResult.Failure.Internal -> context.getString(R.string.check_internal)
        else -> context.getString(R.string.check_unknown)
    }
}

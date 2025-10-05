package com.capstone.nongglenonggle.core.design_system

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun NonggleTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider {
        content()
    }
}

object NonggleTheme {
    val colors: NonggleColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

    val typography: NonggleTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current
}
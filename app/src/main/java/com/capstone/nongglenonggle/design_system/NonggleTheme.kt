package com.capstone.nongglenonggle.design_system

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object NonggleTheme {
    val colors: NonggleColors
    @Composable
    @ReadOnlyComposable
    get()
 = LocalColors.current

    val typography: NonggleTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current
}
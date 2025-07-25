package com.capstone.nongglenonggle.design_system

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

fun nongleColors() = NonggleColors()
fun nongleFonts() = NonggleTypography()

@Composable
fun NongleTheme(
    colors: NonggleColors = nongleColors(),
    typography: NonggleTypography = nongleFonts(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    SideEffect {
        if(!view.isInEditMode) {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.white.toArgb()
        }
    }
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides nongleFonts()
    ) {
        content()
    }
}
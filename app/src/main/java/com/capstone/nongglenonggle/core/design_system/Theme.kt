package com.capstone.nongglenonggle.core.design_system

import android.app.Activity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

fun nongleColors() = NonggleColors()
fun nongleFonts() = NonggleTypography()

@Composable
fun NongleTheme(
    colors: NonggleColors = nongleColors(),
    typography: NonggleTypography = nongleFonts(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            color = Color.White
        ) {
            content()
        }
        val view = LocalView.current
        DisposableEffect(view) {
            val window = (view.context as? Activity)?.window
            if(window != null) {
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = true // 밝은 배경이면 true
                controller.isAppearanceLightNavigationBars = true
            }
            onDispose {  }
        }
    }
}
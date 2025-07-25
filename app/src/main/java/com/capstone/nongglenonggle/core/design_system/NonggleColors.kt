package com.capstone.nongglenonggle.core.design_system

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class NonggleColors (
    val black: Color = Color(0xFF1E1E1E),
    val white: Color = Color(0xFFFFFFFF),
    val m1: Color = Color(0xFF326B48),
    val m2: Color = Color(0xFF488961),
    val m3: Color = Color(0xFF6FAB86),
    val m4: Color = Color(0xFFA1CAB1),
    val m5: Color = Color(0xFFE5EBDD),
    val m6: Color = Color(0xFFEBF0EC),
    val m7: Color = Color(0xFFF4F8F5),
    val s1: Color = Color(0xFFFFA441),
    val error: Color = Color(0xFFCB3C3C),
    val g1: Color = Color(0xFF4E4E4E),
    val g2: Color = Color(0xFF757575),
    val g3: Color = Color(0xFFB0B0B0),
    val g4: Color = Color(0xFFF3F3F3),
    val g5: Color = Color(0xFFFAFAF9),
    val g_line: Color = Color(0xFFE0E0E0),
    val g_line_light: Color = Color(0xFFECECEC),
    val unactive: Color = Color(0xFF6FAB86),
    val background: Color = Color(0xFFF6F4F1),
    val bg: Color = Color(0xFFFDFDFC),
    val shadow: Color = Color(0xFF1E1E1E),
    )

val LocalColors = staticCompositionLocalOf { NonggleColors() }
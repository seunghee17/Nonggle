package com.capstone.nongglenonggle.presentation.view.worker.resume.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import com.capstone.nongglenonggle.R

class ExposedDropMenuStateHolder {
    var enabled by mutableStateOf(false)
    var value by mutableStateOf("")
    var selectedIndex by mutableStateOf(-1)
    var size by mutableStateOf(Size.Zero)
    fun getIcon(): Int = if (enabled) {
        R.drawable.caretup
    } else {
        R.drawable.caretdown
    }

    val items = (1..31).map { "${it}일" }
    fun onEnabled(newValue: Boolean) {
        enabled = newValue
    }

    fun onSelectedIndex(newValue: Int) {
        selectedIndex = newValue
        value = items[selectedIndex]
    }

    fun onSize(newValue: Size) {
        size = newValue
    }
}

@Composable
fun rememberExposedMenuStateHolder() = remember {
    ExposedDropMenuStateHolder()
}
package com.capstone.nongglenonggle.presentation.view.worker.resume.parent_component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.capstone.nongglenonggle.R

class ExposedDropMenuStateHolder {
    var enabled by mutableStateOf(false)
    var value by mutableStateOf("")
    var selectedIndex by mutableStateOf(-1)
    var size by mutableStateOf(Size.Zero)
    val icon: Int
    @Composable get() = if(enabled) {
       R.drawable.caretup
    } else {
        R.drawable.caretdown
    }

    val items = (1..31).map { "${it}일" }
    fun onEabled(newValue: Boolean) {
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

@Preview
@Composable
fun PreviewDropDownMenu() {
    val stateHolder = rememberExposedMenuStateHolder() //매개변수로 주어져야하는 값

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            OutlinedTextField(
                modifier = Modifier.onGloballyPositioned { stateHolder.onSize(it.size.toSize()) },
                value = stateHolder.value,
                onValueChange = {},
                label = { Text(text = "label") },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = stateHolder.icon),
                        contentDescription = null,
                        Modifier.clickable {
                            stateHolder.onEabled(!(stateHolder.enabled))
                        }
                    )
                },
            )
            DropdownMenu(
                modifier = Modifier.width(with(LocalDensity.current) {stateHolder.size.width.toDp()}),
                expanded = stateHolder.enabled,
                onDismissRequest = {
                    stateHolder.onEabled(false)
                }
            ) {
                stateHolder.items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = { Text(text = s) },
                        onClick = {
                            stateHolder.onSelectedIndex(index)
                            stateHolder.onEabled(false)
                        }
                    )
                }
            }
        }
    }

}
package com.capstone.nongglenonggle.core
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

//Modifier 확장함수로 ripple 효과를 비활성화하기 위함
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable ( indication = null,
        interactionSource = remember {
            MutableInteractionSource()
        }) {
        onClick()
    }
}
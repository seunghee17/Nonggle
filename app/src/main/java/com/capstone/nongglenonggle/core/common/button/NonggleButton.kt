package com.capstone.nongglenonggle.core.common.button

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.composed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme

@Composable
fun NonggleButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentColor: Color,
    disableContentColor: Color? = null,
    border: BorderStroke? = null,
    roundedCorner: Dp? = null,
    backgroundColor: Color,
    disableBackGroundColor: Color? = null,
    onClick: () -> Unit,
    contentPadding: PaddingValues? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(roundedCorner ?: 0.dp),
        border = border,
        contentPadding = contentPadding ?: PaddingValues(all = 0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = disableBackGroundColor ?: backgroundColor,
            disabledContentColor = disableContentColor ?: contentColor,
        ),
        interactionSource = interactionSource,
    ) {
        content()
    }
}

@Composable
fun FullButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    titleText: String,
    backgroundColor: Color,
    disableBackGroundColor: Color,
    titleTextStyle: TextStyle
) {
    NonggleButton(
        modifier = Modifier.wrapContentHeight(),
        enabled = enabled,
        contentColor = Color.White,
        backgroundColor = backgroundColor,
        disableContentColor = disableBackGroundColor,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 20.dp),
        content = {
            Text(
                text = titleText,
                style = titleTextStyle
            )
        }
    )
}

@Composable
fun ImageButton(
    modifier: Modifier,
    onClick: () -> Unit,
    titleText: String,
    contentColor: Color,
    backgroundColor: Color,
    titleTextStyle: TextStyle,
    imageResource: Int
) {
    NonggleButton(
        modifier = modifier,
        roundedCorner = 4.dp,
        enabled = true,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 14.dp),
        content = {
            Row(
                modifier = Modifier.wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = imageResource),
                    modifier = Modifier
                        .size(width = 24.dp, height = 24.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = titleText,
                    style = titleTextStyle
                )
            }
        }
    )
}

@Composable
fun ContainedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    titleText: String,
    titleTextStyle: TextStyle,
    backgroundColor: Color,
    pressBackgroundColor: Color,
    disableBackGroundColor: Color,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    NonggleButton(
        modifier = modifier,
        enabled = enabled,
        contentColor = Color.White,
        roundedCorner = 4.dp,
        backgroundColor = if(isPressed) pressBackgroundColor else backgroundColor,
        disableContentColor = disableBackGroundColor,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        interactionSource = interactionSource,
        content = {
            Text(
                text = titleText,
                style = titleTextStyle
            )
        }
    )
}

@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    enableColor: Color,
    pressedColor: Color,
    disableContentColor: Color? = null,
    titleText: String,
    titleTextStyle: TextStyle,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    NonggleButton(
        modifier = modifier.wrapContentHeight(),
        enabled = enabled,
        contentColor = if(isPressed) pressedColor else enableColor,
        disableContentColor = disableContentColor,
        roundedCorner = 4.dp,
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp, color = if(isPressed) pressedColor else enableColor),
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        interactionSource = interactionSource,
        content = {
            Text(
                textAlign = TextAlign.Center,
                text = titleText,
                style = titleTextStyle
            )
        }
    )
}

@Composable
fun OutlinedIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    contentColor: Color,
    pressedContentColor: Color,
    disableContentColor: Color,
    pressedBorderColor: Color,
    borderColor: Color,
    titleText: String,
    titleTextStyle: TextStyle,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    NonggleButton(
        modifier = modifier,
        enabled = enabled,
        contentColor = if(isPressed) pressedContentColor else contentColor,
        disableContentColor = disableContentColor,
        roundedCorner = 4.dp,
        backgroundColor = borderColor,
        border = BorderStroke(width = 1.dp, color = if(isPressed) pressedBorderColor else borderColor),
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        interactionSource = interactionSource,
        content = {
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titleText,
                    style = titleTextStyle
                )
                Spacer(modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.right_small),
                    modifier = modifier.size(width = 24.dp, height = 24.dp),
                    tint = if(isPressed) pressedContentColor else contentColor,
                    contentDescription = null,
                )
            }
        }
    )
}
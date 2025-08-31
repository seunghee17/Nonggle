package com.capstone.nongglenonggle.core.common.textfield


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.nongglenonggle.R

@Composable
fun NonggleTextField(
    modifier: Modifier = Modifier,
    textFieldType: TextFieldType,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = NonggleTheme.typography.b1_main,
    textColor: Color = Color(0xFF1E1E1E),
    focusedColor: Color = NonggleTheme.colors.m1,
    errorColor: Color = NonggleTheme.colors.error,
    successColor: Color = NonggleTheme.colors.m1,
    disabledColor: Color = NonggleTheme.colors.g_line,
    enabledColor: Color = NonggleTheme.colors.g_line,
    containerColor: Color = NonggleTheme.colors.g4,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit),
    supportText: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    maxLines: Int = 1,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    label: @Composable (() -> Unit)? = null,
    ) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        label?.let {
            Spacer(modifier = Modifier.height(8.dp))
            it()
        }
        when(textFieldType) {
            TextFieldType.Standard -> TextField(
                modifier = modifier
                    .padding(PaddingValues(start = 4.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)),
                value = value,
                supportingText = supportText,
                enabled = enabled,
                isError = isError,
                onValueChange = onValueChange,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                placeholder = placeholder,
                maxLines = maxLines,
                textStyle = textStyle.copy(color = textColor),
                readOnly = readOnly,
                trailingIcon = trailingIcon,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = focusedColor,
                    unfocusedIndicatorColor = if(isSuccess) successColor else enabledColor,
                    disabledIndicatorColor = disabledColor,
                    errorIndicatorColor = errorColor,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                )
            )
            TextFieldType.Filled -> OutlinedTextField(
                modifier = modifier,
                enabled = enabled,
                readOnly = readOnly,
                value = value,
                onValueChange = onValueChange,
                supportingText = supportText,
                textStyle = textStyle.copy(color = textColor),
                shape = shape,
                placeholder = placeholder,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                maxLines = maxLines,
                trailingIcon = trailingIcon,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = focusedColor,
                    unfocusedBorderColor = if(isSuccess) successColor else enabledColor,
                    disabledBorderColor = disabledColor,
                    errorBorderColor = errorColor,
                    focusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    errorContainerColor = containerColor,
                )
            )
        }
    }
}

enum class TextFieldType {
    Standard, Filled
}

@Preview
@Composable
fun PreviewTextField() {
    var title by remember { mutableStateOf("") }
    var isError = false
    var title2 by remember { mutableStateOf("") }
    var isError2 = false
    Column {
        NonggleTextField(
            textFieldType = TextFieldType.Standard,
            value = title,
            onValueChange = {title = it},
            modifier = Modifier.padding(vertical = 10.dp , horizontal = 24.dp),
            placeholder = {
                Text(text = "힌트텍스트", style = NonggleTheme.typography.b1_main)
            },
            isError = isError,
            isSuccess = false,
        )

        NonggleTextField(
            textFieldType = TextFieldType.Filled,
            value = title2,
            onValueChange = {title = it},
            modifier = Modifier.padding(vertical = 10.dp , horizontal = 24.dp),
            placeholder = {
                Text(text = "힌트텍스트", style = NonggleTheme.typography.b1_main)
            },
            isError = isError2,
            trailingIcon = {
                Image(
                    modifier = Modifier
                        .size(width = 20.dp, height = 20.dp),
                    painter = painterResource(id =R.drawable.bell,),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.Green)
                )
            },
            isSuccess = true,
        )
    }
}
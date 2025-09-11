package com.capstone.nongglenonggle.core.common.dialog

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.ContainedButton
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo

//취소 확인 버튼 포함한 다이얼로그
@Composable
fun NonggleConfirmDialog(
    onDismissRequest: () -> Unit,
    context: Context,
    title: String,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,

        ),
        content = {
            Card(
                shape = RoundedCornerShape(16.dp),
            ) {
                Column {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            Text(
                                text = title,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W500,
                                    fontFamily = spoqahanSansneo,
                                    color = Color(0xff1E1E1E)
                                )
                            )
                            Spacer(modifier = Modifier.padding(top = 32.dp))
                            content
                            Spacer(modifier = Modifier.padding(top = 32.dp))
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ContainedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            onClick = onDismissRequest,
                            backgroundColor = NonggleTheme.colors.g4,
                            titleText = context.getString(R.string.취소),
                            titleTextStyle = TextStyle(
                                fontFamily = spoqahanSansneo,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = NonggleTheme.colors.g1
                            ),
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        ContainedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            onClick = onDismissRequest,
                            titleText = context.getString(R.string.확인),
                            titleTextStyle = TextStyle(
                                fontFamily = spoqahanSansneo,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 28.dp))
                }
            }
        }
    )
}
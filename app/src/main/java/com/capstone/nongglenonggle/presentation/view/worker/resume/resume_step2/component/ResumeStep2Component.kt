package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable
import com.capstone.nongglenonggle.data.model.worker.ResumeStep2UserCareerListItem

@Composable
fun careerItem(
    resumeUserCareerListItem: ResumeStep2UserCareerListItem,
    deleteAction: () -> Unit,
    modifyAction: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, NonggleTheme.colors.g_line_light),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column {
            Row {
                bulletComponent()
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = resumeUserCareerListItem.careerTitle,
                    style = TextStyle(
                        fontFamily = spoqahanSansneo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        .drawBehind {
                            drawRoundRect(
                                color = Color(0xFFE5EBDD),
                                size = this.size,
                                cornerRadius = CornerRadius(4)
                            )
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = resumeUserCareerListItem.careerPeriod,
                    style = TextStyle(
                        fontFamily = spoqahanSansneo,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )
                Image(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(24.dp)
                        .noRippleClickable {
                            modifyAction()
                        },
                    painter = painterResource(id = R.drawable.ppencil),
                    contentDescription = null
                )
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                        .noRippleClickable {
                            deleteAction()
                        },
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 10.dp),
                text = resumeUserCareerListItem.careerPeriodDetail,
                style = TextStyle(
                    fontFamily = spoqahanSansneo,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = NonggleTheme.colors.g2
                )
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = resumeUserCareerListItem.careerContent,
                style = TextStyle(
                    fontFamily = spoqahanSansneo,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = NonggleTheme.colors.g2
                )
            )
        }
    }
}

@Composable
fun bulletComponent(color: Color = NonggleTheme.colors.m1) {
    Box(
        modifier = Modifier
            .size(6.dp)
            .drawBehind {
                drawCircle(
                    color = color,
                    radius = 3f
                )
            }
    )
}
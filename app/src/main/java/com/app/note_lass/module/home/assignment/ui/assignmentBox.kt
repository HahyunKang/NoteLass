package com.app.note_lass.module.home.assignment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R
import com.app.note_lass.ui.component.CircleArc
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack

@Composable
fun assignmentBox(
    title: String,
    subject: String,
    num: Int,
    backGroundColor: Color,
    contentColor: Color,
    ratio: Float
){
    Box(
        modifier= Modifier
            .shadow(
                elevation = 7.dp,
                shape =RoundedCornerShape(size = 8.dp),
                ambientColor = Color( 0x0A26282B),
                spotColor = Color(0x3D26282B)
            )
            .background(
                color = Color(0xFFFFFFFF),
            )
            .fillMaxSize()
    ){
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Text(text = subject,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(600),
                        color = PrimarayBlue,
                    )
                )
                Text(text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(600),
                        color = PrimaryBlack,
                    )
                )
            }

            Box(
                modifier= Modifier.size(48.dp)
            ){
                CircleArc(
                    percentage = ratio,
                    number = num ,
                    backgroundColor = backGroundColor,
                    color = contentColor,
                    radius = 24.dp
                )
            }
        }
    }

}
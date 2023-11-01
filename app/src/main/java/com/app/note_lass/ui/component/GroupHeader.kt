package com.app.note_lass.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R
import com.app.note_lass.ui.theme.PrimarayBlue

@Composable
fun GroupHeader(
    title : String,
    teacherName : String,
    subject: String,
    onClick : () -> Unit = {}
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier= Modifier.padding(
            start = 15.dp, bottom = 10.dp
        )
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
    ){
        CircleIcon(title = subject)
        Spacer(Modifier.width(10.dp))
        Column(){
            Text(title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF26282B),
                ) )

            Text(teacherName,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                ) )
        }
    }


}


@Composable
fun CircleIcon(title : String){

    Box(
        modifier = Modifier
            .border(width = 1.5.dp, color = PrimarayBlue, shape = CircleShape)
            .padding(1.5.dp)
            .size(40.dp)
            .background(color = Color(0xFFEDEDFF), shape = CircleShape)
    ) {

        Text(title,
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF4849FF),
                textAlign = TextAlign.Center,
            )
            )
    }
}

@Composable
@Preview
fun groupPreview(){
  //  GroupHeader(title = "노트고등학교 3학년 1반 문학", teacherName = "김태연 선생님", subject ="영" )
}
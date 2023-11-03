package com.app.note_lass.module.group.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.app.note_lass.module.group.data.applicationList.ApplicationStudent
import com.app.note_lass.module.group.data.join.JoinStudentInfo
import com.app.note_lass.module.group.data.studentList.Student
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun ApplicationStudent(
    id: Int,
    studentInfo : JoinStudentInfo,
    onClickAccept : (Int)-> Unit={},
    onClickDecline : (Int)-> Unit ={},
)
{
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = id.toString(),
            style = NoteLassTheme.Typography.twenty_700_pretendard,
            color = PrimaryBlack,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier.wrapContentHeight()
                    .weight(5f)

        ) {

        Text(
            text = studentInfo.name,
            style = NoteLassTheme.Typography.twenty_700_pretendard,
            color = PrimaryBlack,
        )
            Text(
                text = "${studentInfo.school}/${studentInfo.grade}학년 ${studentInfo.classNum}반",
                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                color = PrimaryGray,
            )
        }
        Box(
            modifier= Modifier
                .wrapContentSize()
                .background(color =Color(0x339EA4AA), shape = RoundedCornerShape(size = 20.dp))
                .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 3.dp)
                .clickable { onClickDecline(studentInfo.userId.toInt()) }
                .weight(1f)
        ){
            Text("거절",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                    textAlign = TextAlign.Center,
                )
            )
        }
        Box(
            modifier= Modifier
                .wrapContentSize()
                .background(color = Color(0xFFEDEDFF), shape = RoundedCornerShape(size = 20.dp))
                .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 3.dp)
                .clickable { onClickAccept(studentInfo.userId.toInt()) }
                .weight(1f)
        ){
            Text("수락",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF4849FF),
                    textAlign = TextAlign.Center,
                )
            )
        }

    }
}

@Preview
@Composable
fun previewJoinList(){
    val studentInfo = JoinStudentInfo(1,"광남고등학교",1,1,"강하하")
    com.app.note_lass.module.group.ui.component.ApplicationStudent(1,studentInfo)
}
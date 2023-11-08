package com.app.note_lass.module.group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.module.group.ui.component.AssignmentDoneStatus
import com.app.note_lass.module.group.ui.component.AssignmentNotSubmit
import com.app.note_lass.module.group.ui.viewModel.GroupForStudentViewModel
import com.app.note_lass.ui.component.SectionHeader
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack
import okhttp3.internal.notifyAll


@Composable
fun GroupStudentScreen(
    studentViewModel: GroupForStudentViewModel = hiltViewModel()
){

    val noticeState= studentViewModel.studentNoticeState
    Row(
        modifier = Modifier
            .padding
                (
                horizontal = 40.dp,
                vertical = 30.dp
            )
    )
    {

        Row(
            modifier = Modifier.weight(2f)
        ){
            Column(modifier =  Modifier.weight(2f)) {

                Box(modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = 7.dp,
                        shape = RoundedCornerShape(size = 8.dp),
                        ambientColor = Color(0x0A26282B),
                        spotColor = Color(0x3D26282B)
                    )
                    .background(
                        color = Color(0xFFFFFFFF)
                    )
                    .padding(horizontal = 24.dp)
                ){
                    SectionHeader(title = "공지")
                    NoticePreviewScreenForStudent(noticeList =noticeState.value.noticeList)

                }
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier
                    .weight(1.3f)
                    .shadow(
                        elevation = 7.dp,
                        shape = RoundedCornerShape(size = 8.dp),
                        ambientColor = Color(0x0A26282B),
                        spotColor = Color(0x3D26282B)
                    )
                    .background(
                        color = Color(0xFFFFFFFF)
                    )
                    .padding(horizontal = 24.dp)
                ){

                    Text("과제 현황",
                        style = NoteLassTheme.Typography.twenty_700_pretendard,
                        color = PrimaryBlack,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    AssignmentNotSubmit(title = "레포트 작성", deadline = 6)
                    Spacer(modifier = Modifier.height(12.dp))

                    AssignmentDoneStatus(
                        title = "과제1",
                        ratio = 10.toFloat()/23,
                        totalScore = 23,
                        score = 10,
                        isGraded = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    AssignmentDoneStatus(
                        title = "과제2",
                        ratio = null ,
                        totalScore = 10,
                        score = 0,
                        isGraded = false
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    AssignmentDoneStatus(
                        title = "과제3",
                        ratio = 3.toFloat()/ 10.toFloat() ,
                        totalScore = 10,
                        score = 3,
                        isGraded = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    AssignmentDoneStatus(
                        title = "과제3",
                        ratio = 9.toFloat()/ 10 ,
                        totalScore = 10,
                        score = 9,
                        isGraded =true
                    )

                }

            }

            Box(modifier = Modifier
                .weight(1f)
                .shadow(
                    elevation = 7.dp,
                    shape = RoundedCornerShape(size = 8.dp),
                    ambientColor = Color(0x0A26282B),
                    spotColor = Color(0x3D26282B)
                )
                .background(
                    color = Color(0xFFFFFFFF)
                )
                .padding(horizontal = 24.dp)
            ){

            }

        }









    }
}
@Preview
@Composable
fun PreviewGroupStudentScreen(){
   // GroupStudentScreen()
}
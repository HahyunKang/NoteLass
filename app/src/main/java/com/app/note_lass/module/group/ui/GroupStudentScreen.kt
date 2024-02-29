package com.app.note_lass.module.group.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.module.group.ui.component.AssignmentDoneStatus
import com.app.note_lass.module.group.ui.component.AssignmentNotSubmit
import com.app.note_lass.module.group.ui.viewModel.GroupForStudentViewModel
import com.app.note_lass.module.student.ui.MaterialListScreen
import com.app.note_lass.ui.component.AppBarForStudentInGroup
import com.app.note_lass.ui.component.AppBarForTeacherInGroup
import com.app.note_lass.ui.component.DialogSelfEvaluationForStudent
import com.app.note_lass.ui.component.SectionHeader
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack
import okhttp3.internal.notifyAll


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GroupStudentScreen(
    groupInfo : String,
    onTouchNoticeClick : (Long) -> Unit,
    onTouchNoticeListClick: () -> Unit,
    goBack : () -> Unit,
    onClickLogout : () -> Unit,
    studentViewModel: GroupForStudentViewModel = hiltViewModel()
) {

    val noticeState = studentViewModel.studentNoticeState
    val materialState = studentViewModel.studentMaterialState
    val isDialogSelfEvaluation = remember{
        mutableStateOf(false)
    }

    if(isDialogSelfEvaluation.value){
        DialogSelfEvaluationForStudent(setShowDialog = {isDialogSelfEvaluation.value = it})
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarForStudentInGroup(
                title = groupInfo,
                badgeCount = 12,
                onSelfEvaluationClick = {
                    isDialogSelfEvaluation.value = true
                },
                onClickLogout = onClickLogout,
                goBack= goBack
            )
        },
        containerColor = Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {
            Row(
                modifier = Modifier.padding(
                    top = it.calculateTopPadding() + 30.dp,
                    bottom = 20.dp,
                    start = 30.dp,
                    end = 30.dp
                )
            ) {
                Column(modifier = Modifier.weight(2f)) {

                    Column(
                        modifier = Modifier
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
                            .padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            SectionHeader(title = "공지", onTouchIcon = onTouchNoticeListClick)
                        }
                        Box(modifier = Modifier.weight(4f)) {
                            NoticePreviewScreenForStudent(
                                noticeList = noticeState.value.noticeList,
                                onClick = onTouchNoticeClick
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(
                        modifier = Modifier
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
                    ) {

                        Text(
                            "과제 현황",
                            style = NoteLassTheme.Typography.twenty_700_pretendard,
                            color = PrimaryBlack,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        AssignmentNotSubmit(title = "레포트 작성", deadline = 6)
                        Spacer(modifier = Modifier.height(12.dp))

                        AssignmentDoneStatus(
                            title = "과제1",
                            ratio = 10.toFloat() / 23,
                            totalScore = 23,
                            score = 10,
                            isGraded = true
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        AssignmentDoneStatus(
                            title = "과제2",
                            ratio = null,
                            totalScore = 10,
                            score = 0,
                            isGraded = false
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        AssignmentDoneStatus(
                            title = "과제3",
                            ratio = 3.toFloat() / 10.toFloat(),
                            totalScore = 10,
                            score = 3,
                            isGraded = true
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        AssignmentDoneStatus(
                            title = "과제3",
                            ratio = 9.toFloat() / 10,
                            totalScore = 10,
                            score = 9,
                            isGraded = true
                        )

                    }

                }
                Spacer(modifier = Modifier.width(24.dp))

                Box(
                    modifier = Modifier
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
                ) {

                    if (
                        materialState.value.isSuccess
                    ) {
                        MaterialListScreen(list = materialState.value.result!!)
                    } else {
                        Log.e("loading in material", "")
                    }
                }
            }

            })


        }







@Preview
@Composable
fun PreviewGroupStudentScreen(){
   // GroupStudentScreen()
}
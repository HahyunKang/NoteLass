package com.app.note_lass.module.group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.module.group.ui.viewModel.GroupForTeacherViewModel
import com.app.note_lass.ui.component.AppBar
import com.app.note_lass.ui.component.IconAndText
import com.app.note_lass.ui.component.SectionHeader
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryPurple

@Composable
fun GroupTeacherScreen(
    viewModel : GroupForTeacherViewModel = hiltViewModel()
){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = "그룹",
                badgeCount = 12,
                role = Role.STUDENT,
                isGroupButton = false,
                onGroupClick ={
                }
            )

        },
        containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {

            val studentListState = viewModel.studentListState
            Row(
                modifier = Modifier
                    .padding
                        (
                        horizontal = it.calculateTopPadding(),
                        vertical = 30.dp
                    )
            )
            {
                Column(
                    modifier = Modifier.weight(2f)
                ) {
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

                        SectionHeader(title = "공지/과제")


                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    val assignmentList = listOf("과제1", "과제2", "과제3")
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
                                color = Color(0xFFFFFFFF),
                            )
                            .padding(horizontal = 24.dp)
                    ) {
                        SectionHeader(title = "과제별 성적 열람")
                        Spacer(modifier = Modifier.height(16.dp))

                        assignmentList.forEachIndexed { index: Int, assignment: String ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {
                                IconAndText(
                                    icon = R.drawable.group_grade_small,
                                    iconColor = PrimarayBlue,
                                    text = assignment
                                )
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

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
                                color = Color(0xFFFFFFFF),
                            )
                            .padding(start = 24.dp)
                            .fillMaxWidth()

                    ) {

                    }
                }

                Spacer(modifier = Modifier.width(24.dp))
                val scrollState = rememberScrollState()

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
                            color = Color(0xFFFFFFFF),
                        )
                        .fillMaxHeight()
                        .padding(start = 24.dp, end = 24.dp)
                ) {

                    SectionHeader(title = "생기부 관리")
                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)


                    ) {

                        if (studentListState.value.isSuccess) {
                            studentListState.value.studentList.forEachIndexed() { index, studentName ->
                                val id = index + 1
                                IconAndText(
                                    icon = R.drawable.group_person_small,
                                    iconColor = PrimarayBlue,
                                    text = "$id  $studentName"
                                )
                                Spacer(modifier = Modifier.height(20.dp))

                            }
                        }
                    }


                }


            }
        })
}



@Composable
@Preview
fun GroupTeacherPreview(){
    GroupTeacherScreen()
}
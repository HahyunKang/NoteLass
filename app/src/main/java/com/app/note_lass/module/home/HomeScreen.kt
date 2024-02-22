package com.app.note_lass.module.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.note_lass.R
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.module.home.assignment.Assignment
import com.app.note_lass.module.home.material.MaterialSection
import com.app.note_lass.module.home.material.NoteSection
import com.app.note_lass.module.home.tab.tabView
import com.app.note_lass.ui.component.AppBar
import com.app.note_lass.ui.component.EmptyContent
import com.app.note_lass.ui.component.GroupHeader
import com.app.note_lass.ui.component.HorizontalCalendar
import com.app.note_lass.ui.component.SectionHeader
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray
import com.app.note_lass.ui.theme.PrimaryGreen
import com.app.note_lass.ui.theme.PrimaryPink
import com.app.note_lass.ui.theme.PrimaryPurple
import com.app.note_lass.ui.theme.PrimaryYellow
import com.app.note_lass.ui.theme.arcGreen
import com.app.note_lass.ui.theme.arcPink
import com.app.note_lass.ui.theme.arcPurple
import com.app.note_lass.ui.theme.arcYellow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    onClickLogout : () -> Unit,
    onClickGroup : (Int,String) -> Unit,
    onClickGroupAll : () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    role : Role,
){

    val materialState = homeViewModel.getLatestUploadMaterialState
    val groupState = homeViewModel.groupListState
    val noteState = homeViewModel.getLatestNoteState
    LaunchedEffect(true) {
        if(role == Role.TEACHER){
            homeViewModel.getLatestMaterial()
        }else{
            homeViewModel.getLatestNote()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = "홈",
                badgeCount = 12,
                role = role,
                isGroupButton = false,
            )
        },
       containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {
            Row(
                modifier = Modifier
                    .padding(
                        top = it.calculateTopPadding() + 30.dp,
                        bottom = 20.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
            ) {

                Column(
                    modifier = Modifier.weight(5f)
                ) {
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 7.dp,
                                shape = RoundedCornerShape(size = 8.dp),
                                ambientColor = Color(0x0A26282B),
                                spotColor = Color(0x3D26282B)
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                            )
                            .weight(2.3f)
                    ) {

                        val list = listOf("학교 주요 공지", "시간표", "오늘의 급식")
                        tabView(titleList = list, navController)
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Box(
                        modifier = Modifier.weight(3f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                "과제",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                    fontWeight = FontWeight(700),
                                    color = PrimaryBlack,
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            val assignmentList = listOf<Assignment>(
                                Assignment("영어", "수능특강 분석", 24, arcPink, PrimaryPink),
                                Assignment("문학", "레포트 작성", 17, arcGreen, PrimaryGreen),
                                Assignment("생명과학", "유전 문제 풀이", 3, arcYellow, PrimaryYellow),
                                Assignment("확률과 통계", "연습 문제 풀이", 0, arcPurple, PrimaryPurple),

                                )
                            val studentNum = 30
                            Box(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 7.dp,
                                        shape = RoundedCornerShape(size = 8.dp),
                                        ambientColor = Color(0x0A26282B),
                                        spotColor = Color(0x3D26282B)
                                    )
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                    )
                                    .fillMaxWidth()
                                    .weight(5f)
                            ) {

                                Text(
                                    text = "준비 중입니다.",
                                    color = PrimaryGray,
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    ),
                                    style = NoteLassTheme.Typography.sixteem_600_pretendard
                                )
//                            LazyVerticalGrid(
//                                modifier = Modifier.fillMaxSize(),
//                                columns = GridCells.Fixed(2),
//                                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                                verticalArrangement = Arrangement.spacedBy(8.dp)
//                            ) {
//
//                                items(assignmentList.size) {
//                                    assignmentBox(
//                                        title = assignmentList[it].title,
//                                        subject = assignmentList[it].subject,
//                                        num = assignmentList[it].num,
//                                        backGroundColor = assignmentList[it].backgroundColor,
//                                        contentColor = assignmentList[it].contentColor,
//                                        ratio = assignmentList[it].num.toFloat() / 30
//                                    )
//
//                                }
//                            }

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(
                        modifier = Modifier.weight(3f)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 7.dp,
                                    shape = RoundedCornerShape(size = 8.dp),
                                    ambientColor = Color(0x0A26282B),
                                    spotColor = Color(0x3D26282B)
                                )
                                .background(
                                    color = Color(0xFFFFFFFF),
                                )
                                .fillMaxSize()
                        ) {

                            if (materialState.value.isSuccess) {
                                if(materialState.value.result!!.isNotEmpty())
                                MaterialSection(
                                    materials = materialState.value.result!!,
                                )else EmptyContent(title = "최근에 열어본 학습자료가 없습니다\n", content =
                                "학습자료를 확인해 주세요")
                            }
                        
                            if (noteState.value.isSuccess) {
                                if(materialState.value.result!!.isNotEmpty()) {
                                    NoteSection(notes = noteState.value.result!!)
                                }else EmptyContent(title = "최근에 열어본 학습자료가 없습니다\n", content =
                                "학습자료를 확인해 주세요")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    modifier = Modifier.weight(3f)

                ) {
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 7.dp,
                                shape = RoundedCornerShape(size = 8.dp),
                                ambientColor = Color(0x0A26282B),
                                spotColor = Color(0x3D26282B)
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                            )
                            .weight(3f)
                    ) {

                        HorizontalCalendar(onSelectedDate = {})
                    }

                    Spacer(modifier = Modifier.height(24.dp))



                    Column(
                        modifier = Modifier
                            .shadow(
                                elevation = 7.dp,
                                shape = RoundedCornerShape(size = 8.dp),
                                ambientColor = Color(0x0A26282B),
                                spotColor = Color(0x3D26282B)
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                            )
                            .weight(2f)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .weight(1f)
                        ) {
                            SectionHeader(title = "내가 속한 그룹", onTouchIcon = onClickGroupAll)
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(3f),
                            verticalArrangement = Arrangement.Top
                        ) {

                            if (groupState.value.isSuccess) {

                              if(groupState.value.groupList.isEmpty()){
                                EmptyContent(title = "내가 속한 그룹이 없습니다.", content = "그룹을 추가해주세요")
                              }else{
                                  val groupList = groupState.value.groupList

                                  groupList.forEachIndexed { index, groupInfo ->
                                      if(index < 3)  GroupHeader(
                                          title = "${groupInfo.school} ${groupInfo.grade}학년 ${groupInfo.classNum}반 ${groupInfo.subject}",
                                          teacherName = groupInfo.teacher + " 선생님",
                                          subject = groupInfo.subject!![0].toString(),
                                          onClick = {
                                              onClickGroup(
                                                  groupInfo.id.toInt(),
                                                  "${groupInfo.school} ${groupInfo.grade}학년 ${groupInfo.classNum}반 ${groupInfo.subject}"
                                              )
                                          }
                                      )
                                  }

                              }

                            }

                        }
                    }
                            }
                        }

        })
}





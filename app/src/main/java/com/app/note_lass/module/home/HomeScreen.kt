package com.app.note_lass.module.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.note_lass.R
import com.app.note_lass.module.group.data.groupInfo
import com.app.note_lass.module.home.assignment.Assignment
import com.app.note_lass.module.home.assignment.ui.assignmentBox
import com.app.note_lass.module.home.tab.tabView
import com.app.note_lass.module.main.MainActivity
import com.app.note_lass.ui.component.GroupHeader
import com.app.note_lass.ui.component.HorizontalCalendar
import com.app.note_lass.ui.component.SectionHeader
import com.app.note_lass.ui.theme.PrimaryBlack
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
    navController: NavController
){

    Row(
        modifier = Modifier.padding(horizontal = 40.dp,
            vertical = 30.dp)
    ){

        Column(
            modifier= Modifier.weight(5f)
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
                    .weight(2.3f)
            ){

                val list = listOf("학교 주요 공지","시간표","오늘의 급식")
                tabView(titleList = list,navController)

                    
                }


            Spacer(modifier =Modifier.height(15.dp))

            Box(
                modifier= Modifier.weight(3f)
            ){
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                   SectionHeader(title = "과제")
                    val assignmentList= listOf<Assignment>(
                        Assignment("영어","수능특강 분석",24, arcPink, PrimaryPink),
                        Assignment("문학","레포트 작성",17, arcGreen, PrimaryGreen),
                        Assignment("생명과학","유전 문제 풀이",3, arcYellow, PrimaryYellow),
                        Assignment("확률과 통계","연습 문제 풀이",0, arcPurple, PrimaryPurple),

                        )
                    val studentNum = 30
                    
                    LazyVerticalGrid(
                        modifier = Modifier.
                        fillMaxSize(),
                        columns = GridCells.Fixed(2) ,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){

                        items(assignmentList.size){
                            assignmentBox(
                                title = assignmentList[it].title,
                                subject = assignmentList[it].subject,
                                num = assignmentList[it].num,
                                backGroundColor = assignmentList[it].backgroundColor,
                                contentColor = assignmentList[it].contentColor,
                                ratio =  assignmentList[it].num.toFloat() / 30
                            )

                        }



                    }



                    }

            }

            Spacer(modifier =Modifier.height(15.dp))

            Box(
                modifier= Modifier.weight(3f)
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
                ) {
                }
            }
        }
        Spacer(modifier =Modifier.width(24.dp))
        Column(
            modifier= Modifier.weight(3f)

        ) {
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
                    .weight(3f)
            ){

                    HorizontalCalendar(onSelectedDate = {})
                }

            Spacer(modifier =Modifier.height(24.dp))

            val groupList = listOf<groupInfo>(
                groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
                groupInfo("노트고등학교 3학년 1반 영어","티파니 선생님","영"),
                groupInfo("노트고등학교 3학년 1반 확률과 통계","이승규 선생님","확")

            )

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
                    .weight(2f)
            ){

                    Column(
                        modifier= Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 15.dp)
                        ) {
                            SectionHeader(title = "내가 속한 그룹")
                        }

                        groupList.forEachIndexed { index, groupInfo ->
                            GroupHeader(title = groupInfo.title, teacherName = groupInfo.name, subject =groupInfo.subject )
                        }

                    }




                }
            }
        }




    }



package com.app.note_lass.module.group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.ui.component.IconAndText
import com.app.note_lass.ui.component.SectionHeader
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryPurple

@Composable
fun GroupTeacherScreen(){


    Row(
        modifier = Modifier
            .padding
                (
                horizontal = 40.dp,
                vertical = 30.dp
            )
    )
    {
        Column(
            modifier = Modifier.weight(2f)
            ) {
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
            ) {

             SectionHeader(title = "공지/과제")
                


            }
            Spacer(modifier = Modifier.height(24.dp))
            val assignmentList = listOf("과제1","과제2","과제3")
            Column(modifier = Modifier
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

                assignmentList.forEachIndexed {
                    index: Int, assignment: String ->
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                    ){
                        IconAndText(icon = R.drawable.group_grade_small, iconColor = PrimarayBlue , text =assignment )
                    }

                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier
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
        val studentList = listOf(
            "강가람","강나윤","고다올" ,"권라인","김마루","나사린","도아름", "류하나" , "박자올" , "백차리" , "송하림" , "신나래" , "안다슬" , "양라윤" ,
            "오가인" , "원하늘" , "유다람" , "이라비" , "임하루" , "장차미" , "전바라" , "정사랑" , "조나나" , "주하림" , "진가은" , "차나리" , "최다올" , "표하윤" , "한마음" , "황하리"
        )
        Spacer(modifier = Modifier.width(24.dp))
        val scrollState = rememberScrollState()

       Column(modifier = Modifier
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
           .padding(start = 24.dp,end = 24.dp)
        ){

           SectionHeader(title = "생기부 관리")
            Spacer(modifier = Modifier.height(24.dp))

           Column(
               modifier= Modifier
                   .verticalScroll(scrollState)


           ) {

               studentList.forEachIndexed() { index, studentName ->
                   val id = index+1
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

@Composable
@Preview
fun GroupTeacherPreview(){
    GroupTeacherScreen()
}
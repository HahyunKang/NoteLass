package com.app.note_lass.module.upload.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.module.group.data.GradeForStudent
import com.app.note_lass.ui.component.IconAndText
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack

@Composable
fun AssignmentGradeForAllScreen(){

    val names = listOf(
        "강가람", "강나윤", "고다올", "권라인", "김마루", "나사린", "도아름", "류하나", "박자올", "백차리",
        "송하림", "신나래", "안다슬", "양라윤", "오가인", "원하늘", "유다람", "이라비", "임하루", "장차미",
        "전바라", "정사랑", "조나나", "주하림", "진가은", "차나리", "최다올", "표하윤", "한마음", "황하리"
    )

    val studentList = names.map { GradeForStudent("10점", it) }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .shadow(
                elevation = 7.dp,
                shape = RoundedCornerShape(size = 8.dp),
                ambientColor = Color(0x0A26282B),
                spotColor = Color(0x3D26282B)
            )
            .background(
                color = Color(0xFFFFFFFF)
            )
            .fillMaxHeight()

    ){
        Text(text = "과제1", style = NoteLassTheme.Typography.twenty_700_pretendard,color = PrimaryBlack,
          modifier = Modifier.padding(24.dp))
        LazyVerticalGrid(columns =GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 23.dp),
            modifier = Modifier.padding(vertical = 14.dp)
        ){
           itemsIndexed(studentList) { index, studentName ->
                val id = index+1
               Row(modifier = Modifier.padding(vertical = 16.dp)) {
                   IconAndText(
                       icon = R.drawable.group_person_small,
                       iconColor = PrimarayBlue,
                       text = "$id  ${studentName.studentName}"
                   )
                   Spacer(modifier = Modifier.width(16.dp))
                   Text(
                       text = studentName.grade,
                       style = NoteLassTheme.Typography.fourteen_600_pretendard,
                       color = PrimarayBlue
                   )
               }



            }
        }


    }

}
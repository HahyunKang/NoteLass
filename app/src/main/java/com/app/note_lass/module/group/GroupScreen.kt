package com.app.note_lass.module.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.note_lass.module.group.data.groupInfo
import com.app.note_lass.ui.component.GroupHeader

@Composable
fun GroupScreen(){

    Box(
        modifier = Modifier
            .padding
                (
                horizontal = 40.dp,
                vertical = 30.dp
            )
            .background(color = Color.White,shape = RoundedCornerShape(size = 8.dp))
    ){
        val list = listOf<groupInfo>(groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"),
            groupInfo("노트고등학교 3학년 1반 문학","김태연 선생님","문"))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ){
            items(list.size){
                GroupHeader(title = list[it].title, teacherName =list[it].name , subject =list[it].subject )
            }
        }
    }

}
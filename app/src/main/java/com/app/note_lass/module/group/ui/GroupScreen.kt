package com.app.note_lass.module.group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.core.navigation.MainNavGraph
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.group.data.groupInfo
import com.app.note_lass.module.group.ui.viewModel.GroupViewModel
import com.app.note_lass.module.main.ui.items
import com.app.note_lass.ui.component.AppBar
import com.app.note_lass.ui.component.CreateGroup
import com.app.note_lass.ui.component.DialogGroupCode
import com.app.note_lass.ui.component.GroupHeader

@Composable
fun GroupScreen(
     viewModel: GroupViewModel = hiltViewModel()
){
    val showFirstDialog = remember {
        mutableStateOf(false)
    }
    val showSecondDialog = remember {
        mutableStateOf(false)
    }
    val groupRequest = remember{
        mutableStateOf(InfoForCreate(1,1,""))
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
         AppBar(title = "그룹",
             badgeCount = 12,
             onArrowClick = { /*TODO*/ },
             isGroupButton = true,
             onGroupClick ={
                 showFirstDialog.value = true
             } )

        },
        containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {

            if(showFirstDialog.value){
                CreateGroup(
                    setShowDialog = {
                                    showFirstDialog.value = it
                    } ,
                    getInfo = {
                        gradeInfo, classInfo, subjectInfo ->
                        groupRequest.value = InfoForCreate(gradeInfo.toInt(),classInfo.toInt(),subjectInfo)
                    }
                ) {
                    showSecondDialog.value = true
                    showFirstDialog.value = false
                    viewModel.createGroup(groupRequest.value)

                }
            }

            if(showSecondDialog.value && viewModel.groupCreateGroupState.value.isSuccess){
                DialogGroupCode(
                    setShowDialog = {
                             showSecondDialog.value = it
                    },
                    code = viewModel.groupCreateGroupState.value.code
                ) {
                }
            }

    Box(
        modifier = Modifier
            .padding
                (
                horizontal = 40.dp,
                vertical = it.calculateTopPadding()
            )
            .background(color = Color.White, shape = RoundedCornerShape(size = 8.dp))
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
    })
}
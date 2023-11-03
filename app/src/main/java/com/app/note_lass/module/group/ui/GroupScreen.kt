package com.app.note_lass.module.group.ui

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.core.navigation.MainNavGraph
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.group.data.groupInfo
import com.app.note_lass.module.group.ui.viewModel.GroupViewModel
import com.app.note_lass.module.main.ui.items
import com.app.note_lass.ui.component.AppBar
import com.app.note_lass.ui.component.CreateGroup
import com.app.note_lass.ui.component.DialogEnterGroup
import com.app.note_lass.ui.component.DialogEnterGroupAccept
import com.app.note_lass.ui.component.DialogGroupCode
import com.app.note_lass.ui.component.GroupHeader
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.supervisorScope

@Composable
fun GroupScreen(
     onClickGroup : (Int) -> Unit,
     viewModel: GroupViewModel = hiltViewModel(),
     protoViewModel : ProtoViewModel = hiltViewModel()
){
    val role  = protoViewModel.token.collectAsState(initial = Token("",Role.STUDENT))

    val showFirstDialog = remember {
        mutableStateOf(false)
    }
    val showSecondDialog = remember {
        mutableStateOf(false)
    }
    val groupRequest = remember{
        mutableStateOf(InfoForCreate(1,1,""))
    }

    val enterCode = remember{
        mutableStateOf(0)
    }
    val state = viewModel.groupListState

    val enterCodeState = viewModel.enterGroupState
    val joinGroupState = viewModel.joinGroupState

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
         AppBar(
             title = "그룹",
             badgeCount = 12,
             role = role.value.role,
             isGroupButton = true,
             onGroupClick ={
                 showFirstDialog.value = true
             },
             )

        },
        containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {

            if(showFirstDialog.value) {
                if (role.value.role == Role.TEACHER) {
                    CreateGroup(
                        setShowDialog = {
                            showFirstDialog.value = it
                        },
                        getInfo = { gradeInfo, classInfo, subjectInfo ->
                            groupRequest.value =
                                InfoForCreate(gradeInfo.toInt(), classInfo.toInt(), subjectInfo)
                        }
                    ) {
                        showSecondDialog.value = true
                        showFirstDialog.value = false
                        viewModel.createGroup(groupRequest.value)

                    }
                }else{
                    DialogEnterGroup(
                        setShowDialog ={
                                       showFirstDialog.value = it
                        } , getCode = {
                            code ->
                            enterCode.value = code.toInt()
                        }
                    ) {
                        viewModel.enterGroup(enterCode.value)
                        showSecondDialog.value = true
                        showFirstDialog.value = false
                    }
                }
            }

            if(showSecondDialog.value && viewModel.groupCreateGroupState.value.isSuccess){
                DialogGroupCode(
                    setShowDialog = {
                        showSecondDialog.value = it
                    },
                    code = viewModel.groupCreateGroupState.value.code
                ) {
                    viewModel.getGroupList()
                    showSecondDialog.value = false
                }
            }


            if(showSecondDialog.value && viewModel.enterGroupState.value.isSuccess){
               DialogEnterGroupAccept(
                   setShowDialog ={
                                  showSecondDialog.value = it
                   } , groupInfo = viewModel.enterGroupState.value.groupInfo
               ) {
                   viewModel.joinGroup()
                   showSecondDialog.value = false
                   viewModel.getGroupList()
               }
            }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = it.calculateTopPadding() + 30.dp,
                bottom = 20.dp,
                start = 30.dp,
                end = 30.dp
            )
            .background(color = Color.White, shape = RoundedCornerShape(size = 8.dp))

    ){
        if(state.value.isSuccess) {
            val groupList = state.value.groupList
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(groupList.size) {
                        GroupHeader(
                            title = "${groupList[it].school} ${groupList[it].grade}학년 ${groupList[it].classNum}반 ${groupList[it].subject}",
                            teacherName = groupList[it].teacher,
                            subject = groupList[it].subject?.get(0).toString(),
                            onClick ={
                                onClickGroup(groupList[it].id.toInt())
                            }
                        )
                    Log.e("groupList",groupList[it].id.toString())
                    }
                }
            }
        }



    })
    }

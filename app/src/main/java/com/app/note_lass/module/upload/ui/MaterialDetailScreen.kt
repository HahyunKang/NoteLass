package com.app.note_lass.module.upload.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.group.ui.component.NoticeDetailInfo
import com.app.note_lass.module.group.ui.component.NoticeDetailInfoForTeacher
import com.app.note_lass.module.upload.ui.viewmodel.MaterialDetailViewModel
import com.app.note_lass.module.upload.ui.viewmodel.NoticeDetailViewModel
import com.app.note_lass.ui.component.AppBarForNotice

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MaterialDetailScreen(
    materialDetailViewModel: MaterialDetailViewModel = hiltViewModel(),
    protoViewModel: ProtoViewModel = hiltViewModel(),
    goBack : () -> Unit,
    goToModify : ()->Unit
){
    val detailState = materialDetailViewModel.materialDetailState
    val role =
        remember {
            mutableStateOf(Token("", "",Role.NONE))
        }
    LaunchedEffect(role.value) {
        Log.e("role in Log(Test)",role.value.role.toString())
        protoViewModel.token.collect { newToken ->
            Log.e("role in Log(Test)",newToken.role.toString())
            role.value = newToken
        }
    }
    val groupInfo =
        remember {
            mutableStateOf(GroupInfo("", "",null))
        }

    LaunchedEffect(groupInfo.value) {
        Log.e("role in Log(Test)",role.value.role.toString())
        protoViewModel.groupInfo.collect {
            groupInfo.value = it
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
       AppBarForNotice(title = "강의자료", onClickBack = goBack)
        },
        containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {
            Row(
                modifier = Modifier
                    .padding
                        (
                        horizontal = 40.dp,
                        vertical = 30.dp + it.calculateTopPadding()
                    )
            ) {
                Box(
                    Modifier
                        .weight(2f)
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
                        .padding(horizontal = 24.dp)
                ) {
                    if(detailState.value.isSuccess) {
                        if(role.value.role == Role.STUDENT || groupInfo.value.groupId == null ) {
                            NoticeDetailInfo(
                                title = detailState.value.result!!.title,
                                content = detailState.value.result!!.content,
                                file = detailState.value.result!!.file
                            )
                        }else{
                            NoticeDetailInfoForTeacher(
                                title = detailState.value.result!!.title,
                                content = detailState.value.result!!.content,
                                file = detailState.value.result!!.file,
                                goToModify = goToModify,
                                goBack = goBack
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.width(20.dp))

                Column(
                    Modifier
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
                        .fillMaxHeight()
                        .padding(horizontal = 24.dp, vertical = 15.dp)
                ) {
                    DashBoardInfo(groupInfo = groupInfo.value,title = "강의자료")

                }

            }
        })
}
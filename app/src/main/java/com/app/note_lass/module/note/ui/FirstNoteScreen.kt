package com.app.note_lass.module.note.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.ui.component.AppBarForNoGroup
import com.app.note_lass.ui.component.AppBarForNote
import com.app.note_lass.ui.component.AppBarForNotice

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FirstNoteScreen(
    protoViewModel : ProtoViewModel = hiltViewModel(),
    onClickLogout : () -> Unit
){
    val role  = protoViewModel.token.collectAsState(initial = Token("", Role.STUDENT))

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarForNote(
                title = "노트",
                badgeCount = 12,
                onClickLogout = onClickLogout
            )
        },
        containerColor = Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding() + 30.dp,
                        bottom = 20.dp,
                        start = 30.dp,
                        end = 30.dp)
                    .background(color = Color.White,shape = RoundedCornerShape(8.dp))

            ) {
                if (role.value.role == Role.STUDENT) NoteForTeacherScreen()
                else NoteForTeacherScreen()
            }
        })
}
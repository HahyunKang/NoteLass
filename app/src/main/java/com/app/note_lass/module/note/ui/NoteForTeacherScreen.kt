package com.app.note_lass.module.note.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.module.note.ui.component.CreateLectureDialog
import com.app.note_lass.module.note.ui.component.Note
import com.app.note_lass.module.note.ui.component.UploadNote
import com.app.note_lass.ui.component.AppBarForNoGroup
import com.app.note_lass.ui.theme.NoteLassTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteForTeacherScreen(
    noteViewModel: NoteViewModel = hiltViewModel()
){
    val state = noteViewModel.noteListState
    val showDialog = remember{
        mutableStateOf(false)
    }

    if(showDialog.value){
        CreateLectureDialog(setShowDialog = {
            showDialog.value = it
        })
    }
            LazyColumn(
                modifier = Modifier
                    .padding(
                        top = 30.dp,
                        bottom = 20.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
            ) {
                item {
                    UploadNote(title = "신규 노트 만들기") {
                        showDialog.value = true
                    }
                }

                if (state.value.isSuccess) {
                    val noteList = state.value.result
                    items(state.value.result!!.size) {
                        Note(noteList?.get(it)!!.title,noteList.get(it).teacher,)
                    }
                }
            }
        }


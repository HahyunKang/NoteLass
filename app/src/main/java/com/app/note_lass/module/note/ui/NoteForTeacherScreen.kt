package com.app.note_lass.module.note.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.module.note.ui.component.CreateLectureDialog
import com.app.note_lass.module.note.ui.component.Note
import com.app.note_lass.module.note.ui.component.UploadNote
import com.app.note_lass.module.assignment.pdfViewerInCompose

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteForTeacherScreen(
    noteViewModel: NoteViewModel = hiltViewModel()
){
    val state = noteViewModel.noteListState
    val showDialog = remember{
        mutableStateOf(false)
    }
    val pdf = remember{
        mutableStateOf(false)
    }
    val pdfurl  = remember{
        mutableStateOf("")
    }


    if(showDialog.value){
        CreateLectureDialog(setShowDialog = {
            showDialog.value = it
        })
    }
            if(!pdf.value)LazyColumn(
                modifier = Modifier
                    .padding(
                        top = 30.dp,
                        bottom = 20.dp,
                        start = 30.dp,
                        end = 30.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    UploadNote(title = "신규 노트 만들기") {
                        showDialog.value = true
                    }
                }

                if (state.value.isSuccess) {
                    val noteList = state.value.result
                    items(state.value.result!!.size) {

                        Note(noteList?.get(it)!!.title,noteList.get(it).teacher, fileUrl = noteList.get(it).fileUrl,
                            onClickAccess ={
                                noteViewModel.accessNote(noteList.get(it).id)
                            },
                            onClickNote = { click, url ->
                                if(click) pdf.value = true
                                pdfurl.value= url
                            }
                        )

                    }
                }
            }else{
                pdfViewerInCompose(uri =pdfurl.value)
            }
        }


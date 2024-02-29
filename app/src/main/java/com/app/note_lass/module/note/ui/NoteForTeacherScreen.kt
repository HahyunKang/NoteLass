package com.app.note_lass.module.note.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.module.assignment.pdfViewerInCompose
import com.app.note_lass.module.note.ui.component.CreateLectureDialog
import com.app.note_lass.module.note.ui.component.Note
import com.app.note_lass.module.note.ui.component.UploadNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileOutputStream

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteForTeacherScreen(
    noteViewModel: NoteViewModel = hiltViewModel()
){
    val state = noteViewModel.noteListState
    val fileState =noteViewModel.getMaterialFileState
    val showDialog = remember{
        mutableStateOf(false)
    }
    val pdf = remember{
        mutableStateOf(false)
    }
    val pdfurl  = remember{
        mutableStateOf("")
    }
    val fileName = remember{
        mutableStateOf("")
    }

    if(showDialog.value){
        CreateLectureDialog(setShowDialog = {
            showDialog.value = it
        })
    }
    val context = LocalContext.current
    LaunchedEffect(fileState.value) {
        if(fileState.value.isSuccess) {

                        val fileMaterial = java.io.File(context.filesDir, "myFile")
                        try {
                            withContext(Dispatchers.IO) {
                                FileOutputStream(fileMaterial)
                            }.use { outputStream ->
                                val buffer = kotlin.ByteArray(1024)
                                var length: kotlin.Int
                                while (fileState.value.result!!.stream.read(buffer)
                                        .also { length = it } != -1
                                ) {
                                    outputStream.write(buffer, 0, length)
                                }
                            }
                            val intent = android.content.Intent(
                                context,
                                com.app.note_lass.module.note.NoteActivity::class.java
                            )
                            if (fileMaterial.absolutePath.contains("pdf")) {
                                Log.e("absolutePath",fileMaterial.absolutePath.toString())
                                intent.putExtra("filePath", fileMaterial.absolutePath)
                                intent.putExtra("pdfTitle", fileName.value)
                            } else {
                                Log.e("absolutePath",fileMaterial.absolutePath.toString())

                                intent.putExtra("photoPath", fileMaterial.absolutePath)
                                intent.putExtra(
                                    "pdfTitle",
                                    intent.putExtra("pdfTitle", fileName.value)
                                )
                            }
                            context.startActivity(intent)

//                        val intent = Intent(context, NoteActivity::class.java)
//                        if(material.files?.get(0)?.originalFileName?.contains("pdf") == true) {
//                            intent.putExtra("filePath", fileMaterial.absolutePath)
//                            intent.putExtra("pdfTitle",material.files[0].originalFileName )
//                        }
//                        else{
//                            intent.putExtra("photoPath", fileMaterial.absolutePath)
//                            intent.putExtra("pdfTitle", material.files?.get(0)?.originalFileName)
//                        }
//                        context.startActivity(intent)
                        } catch (e: java.io.IOException) {
                            e.printStackTrace()
                        }
                    }


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

                        Note(noteList?.get(it)!!.title,noteList.get(it).teacher, fileId = noteList.get(it).fileId,
                            onClickAccess ={
                                noteViewModel.accessNote(noteList.get(it).id)
                                noteViewModel.getFile(noteList.get(it).fileId)
                                fileName.value = noteList.get(it).title
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


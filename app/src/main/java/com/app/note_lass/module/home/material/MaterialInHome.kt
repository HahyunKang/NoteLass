package com.app.note_lass.module.home.material

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.common.File
import com.app.note_lass.module.home.HomeViewModel
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryGray
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun MaterialInHome(
    title : String,
    file : File,
    date: String,
    viewModel : HomeViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val fileState = viewModel.getMaterialFileState

    if(fileState.value.isSuccess&&file.id == fileState.value.result!!.fileId){
        val fileMaterial = java.io.File(context.filesDir, "myFile")
        try {
            FileOutputStream(fileMaterial).use { outputStream ->
                val buffer = ByteArray(1024)
                var length: Int
                while (fileState.value.result!!.stream.read(buffer).also { length = it } != -1) {
                    outputStream.write(buffer, 0, length)
                }
            }
            val intent = Intent(context, NoteActivity::class.java)
            if(file.originalFileName.contains("pdf")) {
                intent.putExtra("filePath", fileMaterial.absolutePath)
                intent.putExtra("pdfTitle", title)
            }
            else{
                intent.putExtra("photoPath", fileMaterial.absolutePath)
                intent.putExtra("pdfTitle",title)
            }
            context.startActivity(intent)
            Log.e("filePath",fileMaterial.absolutePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

// 파일 경로를 Intent에 넣어서 전달

// 파일 경로를 Intent에 넣어서 전달


//        val intent = Intent(context, NoteActivity::class.java).apply {
//            val url = "https://notelass.site/api/file/" + file.id
//            putExtra("pdfString", url)
//            putExtra("pdfTitle", title)
//        }
//        context.startActivity(intent)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .clickable {
                viewModel.getFile(file.id)
            }
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(id = R.drawable.note_lecture_small),contentDescription = null,tint = Color.Unspecified)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = title,
                style = NoteLassTheme.Typography.fourteen_600_pretendard)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = date,
                style = NoteLassTheme.Typography.twelve_600_pretendard,
                color = PrimaryGray
            )

        }
    }
}
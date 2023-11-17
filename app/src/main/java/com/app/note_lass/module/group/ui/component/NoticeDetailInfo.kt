package com.app.note_lass.module.group.ui.component

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.common.AndroidDownLoader
import com.app.note_lass.core.FilePicker.FileManager
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.ui.component.Divider
import com.app.note_lass.ui.component.FileUpload
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun NoticeDetailInfo(
    title : String,
    content : String,
    fileUrl : String?,
    protoViewModel: ProtoViewModel = hiltViewModel()
) {

//    var pdfUri by remember {
//        mutableStateOf<Uri?>(fileUrl?.toUri())
//    }

    val token = protoViewModel.token.collectAsState(Token("", Role.NONE)).value.accessToken
    var fileName by remember { mutableStateOf<String?>(null) }
    var fileSize by remember { mutableStateOf<Long?>(null) }
    val fileManager = FileManager()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp)
    ) {
        Text(
            text = title,
            style = NoteLassTheme.Typography.twenty_700_pretendard,
            color = PrimaryBlack

        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        Text(
            text = content,
            modifier = Modifier.padding(vertical = 45.dp),
            style = NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack
        )
        Divider()

        if (fileUrl != null) {

//            fileName = fileManager.getFileName(context, pdfUri!!)
//            fileSize = fileManager.getFileSize(context, pdfUri!!)

            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(40.dp)
            ) {
                FileUpload(
                    title = "파일",
                    fileSize = "500",
                    onClick = {
                        val intent = Intent(context, NoteActivity::class.java).apply {
                            putExtra("pdfString", fileUrl)
                        }
                       // pdfUri?.path?.let { Log.e("PDFURI", it) }
                        context.startActivity(intent)
//                         val downloader = AndroidDownLoader(context,token!!)
//                         downloader.downloadFile(fileUrl)
                    },
                    onDelete = {
                    //    fileUrl = null
                    }
                )
            }

        }
    }
}


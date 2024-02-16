package com.app.note_lass.module.group.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.common.AndroidDownLoader
import com.app.note_lass.common.File
import com.app.note_lass.core.FilePicker.FileManager
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.ui.component.Divider
import com.app.note_lass.ui.component.FileUpload
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack

@Composable
fun NoticeDetailInfo(
    title: String,
    content: String,
    file: List<File>?,
    protoViewModel: ProtoViewModel = hiltViewModel(),
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
        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(16.dp))

//            fileName = fileManager.getFileName(context, pdfUri!!)
//            fileSize = fileManager.getFileSize(context, pdfUri!!)

        if (file?.isNotEmpty() == true) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                FileUpload(
                    title = file[0].originalFileName,
                    fileSize = (file[0].size.toFloat() / 1000000.0).toString(),
                    onClick = {

                            val fileUrl = "https://notelass.site/api/file/" + file[0].id
                            val downloader =
                                AndroidDownLoader(context, file!![0].originalFileName, token!!)
                            downloader.downloadFile(fileUrl)
                           Log.e("download file", file?.get(0)?.id.toString())

                    },
                    onDelete = {
                        //    fileUrl = null
                    }
                )
            }

        }
    }
    }



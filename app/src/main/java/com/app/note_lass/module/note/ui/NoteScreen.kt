package com.app.note_lass.module.note.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.note_lass.module.main.ui.items
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.ui.component.AppBar

@Composable
fun NoteScreen() {

        var pdfUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val context = LocalContext.current

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                pdfUri = uri
                if (uri != null) {
                    val intent = Intent(context, NoteActivity::class.java).apply {
                        putExtra("pdfUri", uri)
                    }
                    pdfUri?.path?.let { Log.e("PDFURI", it) }
                    context.startActivity(intent)
                }
            }
        )

        Column {
            if (pdfUri != null) {
                // Show the selected PDF file information or preview if needed
            } else {
                Button(
                    onClick = { launcher.launch("application/pdf") },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Select PDF File")
                }
            }
        }

}






package com.app.note_lass.module.home.material

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun MaterialInHome(
    title : String,
    fileUrl : String,
    date: String
){
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize().clickable {
        val intent = Intent(context, NoteActivity::class.java).apply {
            putExtra("pdfString", fileUrl)
            putExtra("pdfTitle",title)
        }
        context.startActivity(intent)
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
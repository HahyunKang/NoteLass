package com.app.note_lass.module.note.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue

@Composable
fun UploadNote(
    title : String,
    onCLickUpload : () -> Unit
){
    Row(modifier = Modifier.fillMaxWidth()){
        Icon(painter = painterResource(id = R.drawable.note_upload_small),tint = PrimarayBlue, contentDescription = null,
            modifier = Modifier.clickable {
                onCLickUpload
            })
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = title ,
            style = NoteLassTheme.Typography.sixteem_600_pretendard,
            color  = PrimarayBlue
        )
    }
}
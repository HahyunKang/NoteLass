package com.app.note_lass.ui.component

import android.graphics.drawable.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.ui.theme.NoteLassTheme

@Composable
fun EmptyContent(
    title : String,
    content : String
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(id = R.drawable.empty_icon_small), contentDescription = null)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text=  title,color = Color(0xFFC9CDD2), style = NoteLassTheme.Typography.fourteen_600_pretendard)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text=  content,color = Color(0xFFC9CDD2), style = NoteLassTheme.Typography.fourteen_600_pretendard)

    }

}
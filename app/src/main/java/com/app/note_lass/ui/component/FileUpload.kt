package com.app.note_lass.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun FileUpload(
     title : String,
    fileSize : String,
     onClick : ()->Unit,
     onDelete : ()->Unit
){

    Box(
        modifier = Modifier.fillMaxWidth()
        .height(36.dp)
        .background(color = Color(0x80C9CDD2),shape = RoundedCornerShape(12.dp))
        .border(1.dp,color = Color(0x80C9CDD2),shape = RoundedCornerShape(12.dp))
        )
    {
        Row(
            modifier = Modifier.align(Alignment.CenterStart)
            .clickable {
            onClick()
            }
                .padding(horizontal = 5.dp)
        ){
            Icon(painter= painterResource(id = R.drawable.group_filemark_small),tint = PrimaryGray, contentDescription = null)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = title + "(" + fileSize + "MB"+")",
                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                color = PrimaryGray)
        }

        Icon(painter= painterResource(
            id = R.drawable.group_filedelete_small),
            tint = PrimaryGray,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd)
                .clickable { onDelete() }
                .padding(end = 5.dp)
        )

    }

}
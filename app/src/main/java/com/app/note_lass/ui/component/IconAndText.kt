package com.app.note_lass.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.note_lass.ui.theme.NoteLassTheme

@Composable
fun IconAndText(
    icon : Int,
    iconColor : Color,
    text : String,
    textColor : Color = Color.Black,
    onClick : () -> Unit = {}
){

    Row(modifier = Modifier.wrapContentWidth()
        .clickable {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
        Icon(painter = painterResource(id = icon),tint= iconColor, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text,
            style = NoteLassTheme.Typography.fourteen_600_pretendard,
            color = textColor)
    }
}
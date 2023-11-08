package com.app.note_lass.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun Divider(){
    androidx.compose.material3.Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = PrimaryGray
    )
}
package com.app.note_lass.ui.component

import android.widget.ToggleButton
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun ToggleButton(
    text : String,
    getSelected : (Boolean) -> Unit
){
    val selected = remember{
        mutableStateOf(false )
    }
    Text(
        text = text,
        modifier =
        if(selected.value)
            Modifier
                .border(1.dp,color = PrimarayBlue,shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 5.dp, vertical = 3.dp)
            .clickable {
                selected.value = !selected.value
                getSelected(false)
            }
        else
            Modifier
                .border(1.dp, PrimaryGray,shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 5.dp, vertical = 3.dp)
                .clickable {
                selected.value = !selected.value
                getSelected(true)
            },
        textAlign = TextAlign.Center
    )
}


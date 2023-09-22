package com.app.note_lass.ui.component

import android.text.Layout.Alignment
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.NotelassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryGray

data class ToggleableInfo(
    val isChecked : Boolean,
    val text : String
)
@Composable
fun CheckBox(modifier : Modifier = Modifier){

    val checkboxes = remember{
        mutableStateListOf(
            ToggleableInfo(
                isChecked = false,
                text= "선생님"
            ),
            ToggleableInfo(
                isChecked = false,
                text= "학생"
            )
        )
    }
    val selectedOption=  remember{
        mutableIntStateOf(2)
    }

    Row(
        modifier = modifier.fillMaxSize()

    ) {
        checkboxes.forEachIndexed { index, info ->
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedOption.intValue == index,
                    onCheckedChange = {
                        isChecked ->
                        checkboxes[index] = info.copy(
                            isChecked= isChecked
                        )
                        selectedOption.intValue = index
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PrimarayBlue,
                        uncheckedColor = PrimaryGray,
                        checkmarkColor = Color.White
                    )
                )
                if(selectedOption.intValue == index) {
                    Text(
                        text = info.text,
                        style = NoteLassTheme.Typography.twenty_600_pretendard,
                        color = Color.Black
                    )
                }else{
                    Text(
                        text = info.text,
                        style = NoteLassTheme.Typography.twenty_600_pretendard,
                        color = PrimaryGray
                    )
                }
            }

        }

    }
}

@Preview
@Composable
fun checkboxPreview(){
    Box(modifier = Modifier
        .padding(top  = 40.dp)
        .size(width = 200.dp, height = 30.dp)

    ){
        CheckBox()
    }
}
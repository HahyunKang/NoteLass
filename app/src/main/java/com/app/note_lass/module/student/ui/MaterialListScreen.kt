package com.app.note_lass.module.student.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.note_lass.module.student.ui.component.MaterialForStudent
import com.app.note_lass.module.upload.data.Material.Material
import com.app.note_lass.ui.theme.NoteLassTheme
import org.w3c.dom.Text

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MaterialListScreen(
    list : List<Material>
) {
    Column(modifier = Modifier.fillMaxHeight()
        .padding(start =13.dp)) {
        Text(text = "학습 자료",
            style = NoteLassTheme.Typography.twenty_700_pretendard,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)){
            items(list.size){
                MaterialForStudent(material = list[it])
            }
        }
    }


}
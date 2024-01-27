package com.app.note_lass.module.home.material

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.note_lass.common.DateFormatter
import com.app.note_lass.common.StringToDate
import com.app.note_lass.module.note.data.Note
import com.app.note_lass.module.upload.data.Material.Material
import com.app.note_lass.ui.component.SectionHeader
import java.lang.Integer.min

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MaterialSection(
    materials : List<Material>
){
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        SectionHeader(title = "최근 업로드한 강의자료")
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ){
            val fiveLatestMaterials = materials.subList(0,5)
            items(fiveLatestMaterials.size){
                MaterialInHome(title = materials[it].title, fileUrl = materials[it].fileUrl, date = DateFormatter(StringToDate(materials[it].createdDate).localDateTime).formattedDate)
            }
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteSection(
    notes: List<Note>
){
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp),
        ) {
        SectionHeader(title = "최근에 열어본 노트")
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)){
            val fiveNotesMaterials = notes.subList(0,min(notes.size,5))

            items(fiveNotesMaterials.size){
                MaterialInHome(title = notes[it].title, fileUrl = notes[it].fileUrl, date = DateFormatter(StringToDate(notes[it].lastAccessed).localDateTime).formattedDate)
            }
        }

    }

}
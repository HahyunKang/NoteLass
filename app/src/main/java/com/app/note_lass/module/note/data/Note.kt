package com.app.note_lass.module.note.data

data class Note(
    val id : Long,
    val title : String,
    val teacher : String,
    val fileId : Long,
    val lastAccessed : String
)

data class NoteAccessedDto(
    val id : Long,
    val title : String,
    val teacher : String,
    val lastAccessed : String
)

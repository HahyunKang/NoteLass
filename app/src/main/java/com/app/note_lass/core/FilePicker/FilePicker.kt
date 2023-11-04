package com.app.note_lass.core.FilePicker

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.IOException

class FileManager() {





    @SuppressLint("Range")
    fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        var displayName: String? = "pdf"
        cursor?.moveToFirst()
        displayName = cursor?.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        cursor?.close()
        return displayName
    }

    fun getFileSize(context: Context, uri: Uri): Long {
        return try {
            context.contentResolver.openFileDescriptor(uri, "r")?.use { parcelFileDescriptor ->
                val size = parcelFileDescriptor.statSize / (1000000).toLong()
                size
            } ?: 0L
        } catch (e: IOException) {
            0L
        }
    }

}


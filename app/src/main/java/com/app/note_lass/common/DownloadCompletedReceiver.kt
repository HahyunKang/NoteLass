package com.app.note_lass.common

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log

class DownloadCompletedReceiver (
) : BroadcastReceiver() {
    private lateinit var downloadManager : DownloadManager
    companion object {
        private var downloadStatusListener: DownloadStatusListener? = null

        fun registerListener(listener: DownloadStatusListener) {
            downloadStatusListener = listener
        }

        fun unregisterListener() {
            downloadStatusListener = null
        }
    }
    override fun onReceive(p0: Context?, p1: Intent?) {
        downloadManager = p0?.getSystemService(DownloadManager::class.java)!!
        if(p1?.action == "android.intent.action.DOWNLOAD_COMPLETE"){
            val id = p1.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1L)
            downloadStatusListener?.onDownloadStatusUpdated(getStatus(id))
            Log.e("downloadFail",getStatus(id))
            if(id!=-1L){
                println("Download with id $id finished")
            }
        }
    }

    override fun getSentFromUid(): Int {
        return super.getSentFromUid()
    }
    private fun getStatus(id: Long): String {
        val query: DownloadManager.Query = DownloadManager.Query()
        query.setFilterById(id)
        var cursor = downloadManager.query(query)
        if (!cursor.moveToFirst()) {
            Log.e(TAG, "Empty row")
            return "Wrong downloadId"
        }

        var columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
        var status = cursor.getInt(columnIndex)
        var columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON)
        var reason = cursor.getInt(columnReason)
        var statusText: String

        when (status) {
            DownloadManager.STATUS_SUCCESSFUL -> statusText = "Successful"
            DownloadManager.STATUS_FAILED -> {
                statusText = "Failed: $reason"
            }
            DownloadManager.STATUS_PENDING -> statusText = "Pending"
            DownloadManager.STATUS_RUNNING -> statusText = "Running"
            DownloadManager.STATUS_PAUSED-> {
                statusText = "Paused: $reason"
            }
            else -> statusText = "Unknown"
        }

        return statusText
    }
}
package com.app.note_lass.common

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri

class AndroidDownLoader(
    private val context : Context,
) : Downloader{
    private val downloadManager = context.getSystemService(DownloadManager :: class.java)

    override fun downloadFile(url: String): Long {

        val request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("pdf")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"notice.pdf")
       if(Uri.parse(url)!=null ) Log.e("url", Uri.parse(url).toString())
        return downloadManager.enqueue(request)
    }

}


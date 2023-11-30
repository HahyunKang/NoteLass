package com.app.note_lass.common

interface Downloader {
    fun downloadFile(url : String) : Long

}

interface DownloadStatusListener {
    fun onDownloadStatusUpdated(status: String)
}
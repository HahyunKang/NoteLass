package com.app.note_lass.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteLassApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }

}
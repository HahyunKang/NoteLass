package com.app.note_lass.module.signup.ui

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Year
@RequiresApi(Build.VERSION_CODES.O)
data class SignupInfo(
    var email: String = "",
    var password: String = "",
    var name: String ="",
    var admissionYear: Int = 0,
    var school: String = "",
    var role: String = "",
    var grade: String ="",
    var studentClass: String = "",
    var studentId: String = ""
)

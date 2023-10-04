package com.app.note_lass.module.signup.ui

import androidx.compose.runtime.MutableState

data class SignupInfo(
    var email: String = "",
    var password: String = "",
    var name : String ="",
    var admissionYear: String = "",
    var school: String = "",
    var role: String = "",
    var grade: String ="",
    var studentClass : String = "",
    var studentId : String = ""
)

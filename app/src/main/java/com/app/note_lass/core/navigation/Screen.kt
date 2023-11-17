package com.app.note_lass.core.navigation

import com.app.note_lass.common.StudentInfo


const val ROOT_ROUTE = "root"
const val SPLASH_ROUTE = "splash"
const val AUTH_ROUTE = "auth"
const val MAIN_ROUTE = "main"
const val HOME_ROUTE = "main/home"
const val GROUP_ROUTE = "main/group"
const val NOTE_ROUTE = "main/note"
const val SETTING_ROUTE = "main/setting"
const val UPLOAD_ROUTE = "main/upload"

sealed class AuthScreen(val route : String){
    object Login : AuthScreen("auth/login")
    object SchoolInfo : AuthScreen("auth/school/info")
    object StudentInfo : AuthScreen("auth/student/info")

    object SignUp : AuthScreen("auth/signup")

}

sealed class HomeScreen(val route : String){
    object Home : HomeScreen("home/home")

    object Notice : HomeScreen("home/notice")
}

sealed class GroupScreen(val route : String){
    object Home : GroupScreen("group/home")
    object GroupForTeacher : GroupScreen("group/teacher/{groupId}"){
        fun passQuery(groupId : Int) : String {
            return "group/teacher/${groupId}"
        }
    }
    object GroupForStudent : GroupScreen("group/student/{groupId}"){
        fun passQuery(groupId : Int) : String {
            return "group/student/${groupId}"
        }
    }



}

sealed class NoteScreen(val route : String){
    object Home : NoteScreen("note/home")
}

sealed class SettingScreen(val route : String){
    object Home : SettingScreen("setting/home")
}

sealed class UploadScreen(val route:String){
    object CreateNotice : GroupScreen("upload/create")
    object NoticeDetail : UploadScreen("upload/detail/notice/{noticeId}"){
        fun passQuery(noticeId : Long) : String {
            return "upload/detail/notice/${noticeId}"
        }
    }
}

sealed class RecordScreen(val route :String){
    object RecordDetail : RecordScreen("record/detail/{userId}/{studentId}/{name}"){
        fun passQuery(userId : Long, studentId : Long, name : String): String{
            return "record/detail/${userId}/${studentId}/${name}"
        }
    }
}
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
    object Reset: AuthScreen("auth/reset")

}

sealed class HomeScreen(val route : String){
    object Home : HomeScreen("home/home")

    object Notice : HomeScreen("home/notice")
    object DashBoard : HomeScreen("home/dashboard")
}

sealed class GroupScreen(val route : String){
    object Home : GroupScreen("group/home")
    object GroupForTeacher : GroupScreen("group/teacher/{groupId}/{groupInfo}"){
        fun passQuery(groupId : Int, groupInfo : String) : String {
            return "group/teacher/${groupId}/${groupInfo}"
        }
    }
    object GroupForStudent : GroupScreen("group/student/{groupId}/{groupInfo}"){
        fun passQuery(groupId : Int, groupInfo : String) : String {
            return "group/student/${groupId}/${groupInfo}"
        }
    }

    object NoticeForStudent : GroupScreen("group/student/notice")

    object DashBoardForTeacher : GroupScreen("group/teacher/dashboard")


}

sealed class NoteScreen(val route : String){
    object Home : NoteScreen("note/home")
}

sealed class SettingScreen(val route : String){
    object Home : SettingScreen("setting/home")
}

sealed class UploadScreen(val route:String){
    object CreateNotice : GroupScreen("upload/create")
    object ModifyDashboard :UploadScreen("upload/modify/{type}/{dashboardId}"){
        fun passQuery(type : String,dashboardId: Long) : String {
            return "upload/modify/${type}/${dashboardId}"
        }
    }
    object NoticeDetail : UploadScreen("upload/detail/notice/{dashboardId}"){
        fun passQuery(dashboardId : Long) : String {
            return "upload/detail/notice/${dashboardId}"
        }
    }
    object MaterialDetail : UploadScreen("upload/detail/material/{dashboardId}"){
        fun passQuery(dashboardId : Long) : String {
            return "upload/detail/material/${dashboardId}"
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
package com.app.note_lass.core.navigation


const val ROOT_ROUTE = "root"
const val SPLASH_ROUTE = "splash"
const val AUTH_ROUTE = "auth"
const val MAIN_ROUTE = "main"
const val HOME_ROUTE = "main/home"
const val GROUP_ROUTE = "main/group"
const val NOTE_ROUTE = "main/note"
const val SETTING_ROUTE = "main/setting"

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

}

sealed class NoteScreen(val route : String){
    object Home : NoteScreen("note/home")
}

sealed class SettingScreen(val route : String){
    object Home : SettingScreen("setting/home")
}
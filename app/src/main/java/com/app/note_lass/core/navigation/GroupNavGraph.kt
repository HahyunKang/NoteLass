package com.app.note_lass.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.app.note_lass.module.group.ui.GroupScreen
import com.app.note_lass.module.group.ui.GroupStudentScreen
import com.app.note_lass.module.group.ui.GroupTeacherScreen

fun NavGraphBuilder.GroupNavGraph(navController: NavController, outerNavController:NavController) {


    navigation(startDestination = GroupScreen.Home.route, route = GROUP_ROUTE) {
        composable(GroupScreen.Home.route) {
         GroupScreen(
             onClickTeacherGroup = {
             navController.navigate(GroupScreen.GroupForTeacher.passQuery(it))
         },
             onClickStudentGroup = {
             navController.navigate(GroupScreen.GroupForStudent.passQuery(it))
         },
             onClickLogout = {
             outerNavController.navigate(AUTH_ROUTE){
                 popUpTo(GroupScreen.Home.route) {
                     inclusive = true
                 }
                 launchSingleTop = true
             }
             }
         )
        }

        composable(
            route = GroupScreen.GroupForTeacher.route,
            arguments = listOf(navArgument(name = "groupId") { type = NavType.IntType }
            ) ) {
            GroupTeacherScreen(onTouchCreateNotice = {
                navController.navigate(UploadScreen.CreateNotice.route)
            },
                onClickStudentRecord = {
                    userId, studentId, name ->
                    navController.navigate(RecordScreen.RecordDetail.passQuery(userId,studentId,name))
                }
            )
        }

        composable(
            route = GroupScreen.GroupForStudent.route,
            arguments = listOf(navArgument(name = "groupId") { type = NavType.IntType }
            )
        ) {
            GroupStudentScreen(
                onTouchNoticeClick = {
                    navController.navigate(UploadScreen.NoticeDetail.passQuery(it))
                }
            )
        }
    }
}


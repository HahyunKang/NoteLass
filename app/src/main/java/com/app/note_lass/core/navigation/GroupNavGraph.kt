package com.app.note_lass.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.app.note_lass.module.group.ui.AssignmentGradeForAllScreen
import com.app.note_lass.module.group.ui.AssignmentUploadScreen
import com.app.note_lass.module.group.ui.GroupScreen
import com.app.note_lass.module.group.ui.GroupStudentScreen
import com.app.note_lass.module.group.ui.GroupTeacherScreen

fun NavGraphBuilder.GroupNavGraph(navController: NavController) {


    navigation(startDestination = GroupScreen.Home.route, route = GROUP_ROUTE) {
        composable(GroupScreen.Home.route) {
         GroupScreen(
             onClickTeacherGroup = {
             navController.navigate(GroupScreen.GroupForTeacher.passQuery(it))
         },
             onClickStudentGroup = {
             navController.navigate(GroupScreen.GroupForStudent.passQuery(it))
         })
        }

        composable(GroupScreen.CreateNotice.route) {
            AssignmentUploadScreen()
        }

        composable(
            route = GroupScreen.GroupForTeacher.route,
            arguments = listOf(navArgument(name = "groupId") { type = NavType.IntType }
            ) ) {
            GroupTeacherScreen(onTouchCreateNotice = {
                navController.navigate(GroupScreen.CreateNotice.route)
            } )
        }

        composable(
            route = GroupScreen.GroupForStudent.route,
            arguments = listOf(navArgument(name = "groupId") { type = NavType.IntType }
            ) ) {
            GroupStudentScreen()
        }
    }
}


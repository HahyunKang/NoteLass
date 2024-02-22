package com.app.note_lass.core.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.app.note_lass.module.group.ui.GroupScreen
import com.app.note_lass.module.group.ui.GroupStudentScreen
import com.app.note_lass.module.group.ui.GroupTeacherScreen
import com.app.note_lass.module.student.ui.NoticeListScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.GroupNavGraph(navController: NavController, outerNavController:NavController) {


    navigation(startDestination = GroupScreen.Home.route, route = GROUP_ROUTE) {
        composable(GroupScreen.Home.route) {
         GroupScreen(
             onClickTeacherGroup = {
                 id, info ->
             navController.navigate(GroupScreen.GroupForTeacher.passQuery(id,info))
         },
             onClickStudentGroup = {
                 id,info ->
             navController.navigate(GroupScreen.GroupForStudent.passQuery(id,info))
         },
             onClickLogout = {
             outerNavController.navigate(AUTH_ROUTE){
                 launchSingleTop = true
             }
             }
         )
        }

        composable(
            route = GroupScreen.GroupForTeacher.route,
            arguments = listOf(
                navArgument(name = "groupId") { type = NavType.IntType },
                navArgument(name = "groupInfo") { type = NavType.StringType}
            ) ) {
            val groupInfo = it.arguments?.getString("groupInfo")
            val context = LocalContext.current
            if (groupInfo != null) {
                GroupTeacherScreen(
                    groupInfo = groupInfo,
                    onTouchCreateNotice = {
                        navController.navigate(UploadScreen.CreateNotice.route)
                    },
                    onClickStudentRecord = {
                            userId, studentId, name ->
                        navController.navigate(RecordScreen.RecordDetail.passQuery(userId,studentId,name))
                    },
                    goBackToGroup = {
                        navController.navigate(GroupScreen.Home.route)
                        Toast.makeText(context,"삭제되었습니다.",Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        composable(
            route = GroupScreen.GroupForStudent.route,
            arguments =
            listOf(
                navArgument(name = "groupId") { type = NavType.IntType },
                navArgument(name = "groupInfo") { type = NavType.StringType}
            )
        ) {
            val groupInfo = it.arguments?.getString("groupInfo")
            if (groupInfo != null) {

                GroupStudentScreen(
                    groupInfo = groupInfo,
                    onTouchNoticeClick = {
                        navController.navigate(UploadScreen.NoticeDetail.passQuery(it))
                    },
                    onTouchNoticeListClick = {
                        navController.navigate(GroupScreen.NoticeForStudent.route)
                    }
                )
            }
        }

        composable(
            route = GroupScreen.NoticeForStudent.route,
            ) {
            NoticeListScreen(
                goToDetailScreen = {
                    navController.navigate(UploadScreen.NoticeDetail.passQuery(it))
                }
            )
        }
    }
}



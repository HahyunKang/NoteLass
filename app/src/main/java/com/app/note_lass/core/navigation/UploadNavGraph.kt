package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.module.upload.ui.AssignmentUploadScreen
import com.app.note_lass.module.upload.ui.NoticeDetailScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.UploadNavGraph(navController: NavController) {

    navigation(startDestination = UploadScreen.NoticeDetail.route, route = UPLOAD_ROUTE) {

        composable(UploadScreen.CreateNotice.route) {
            AssignmentUploadScreen(goBackToGroup = { role,id,groupInfo ->
                if(role== Role.STUDENT)
                    navController.navigate(GroupScreen.GroupForStudent.passQuery(id.toInt(),groupInfo)){
                        launchSingleTop = true
                    }
                else navController.navigate(GroupScreen.GroupForTeacher.passQuery(id.toInt(),groupInfo)){
                    launchSingleTop = true
                }
            })
        }
        composable(route = UploadScreen.NoticeDetail.route,
            arguments = listOf(navArgument(name = "noticeId") { type = NavType.LongType }
            )
        ) {
            NoticeDetailScreen()
        }
    }




}


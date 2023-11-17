package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.app.note_lass.module.group.ui.AssignmentUploadScreen
import com.app.note_lass.module.group.ui.GroupTeacherScreen
import com.app.note_lass.module.group.ui.NoticeDetailScreen
import com.app.note_lass.module.home.HomeScreen
import com.app.note_lass.module.home.NoticeScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.UploadNavGraph(navController: NavController) {

    navigation(startDestination = UploadScreen.NoticeDetail.route, route = UPLOAD_ROUTE) {

        composable(UploadScreen.CreateNotice.route) {
            AssignmentUploadScreen()
        }
        composable(route = UploadScreen.NoticeDetail.route,
            arguments = listOf(navArgument(name = "noticeId") { type = NavType.LongType }
            )
        ) {
            NoticeDetailScreen()
        }
    }




}


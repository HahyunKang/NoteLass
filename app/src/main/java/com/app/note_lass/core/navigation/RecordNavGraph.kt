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
import com.app.note_lass.module.record.ui.StudentRecordScreen
import com.app.note_lass.module.record.ui.StudentRecordUploadScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.RecordNavGraph(navController: NavController) {


    composable(route = RecordScreen.RecordDetail.route,
        arguments = listOf(navArgument(name = "userId") { type = NavType.LongType},
            navArgument(name = "studentId") { type = NavType.LongType},
            navArgument(name = "name") { type = NavType.StringType}
        )
    ) {
        val studentId = it.arguments?.getLong("studentId")
        val studentName = it.arguments?.getString("name")

        StudentRecordUploadScreen(studentId = studentId!!, studentName = studentName!!)
    }





}


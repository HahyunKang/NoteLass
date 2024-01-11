package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(navController: NavHostController,outerNavController: NavController){
    NavHost(navController = navController, startDestination = HOME_ROUTE , route = MAIN_ROUTE)
    {
        HomeNavGraph(navController = navController, outerNavController = outerNavController)

        GroupNavGraph(navController = navController, outerNavController = outerNavController)

        NoteNavGraph(navController = navController)

        SettingNavGraph(navController = navController)

        UploadNavGraph(navController = navController)

        RecordNavGraph(navController = navController)


    }

}
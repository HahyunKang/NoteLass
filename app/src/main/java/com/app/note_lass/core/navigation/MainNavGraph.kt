package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = HOME_ROUTE , route = MAIN_ROUTE)
    {
        HomeNavGraph(navController = navController)

        GroupNavGraph(navController = navController)

        NoteNavGraph(navController = navController)

        SettingNavGraph(navController = navController)

        UploadNavGraph(navController = navController)

        RecordNavGraph(navController = navController)


    }

}
package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.note_lass.module.home.HomeScreen
import com.app.note_lass.module.home.NoticeScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.HomeNavGraph(navController: NavController) {


    navigation(startDestination = HomeScreen.Home.route, route = HOME_ROUTE) {
        composable(HomeScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(HomeScreen.Notice.route) {
            NoticeScreen()
        }

    }
}


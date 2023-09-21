package com.app.note_lass.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.SettingNavGraph(navController: NavController) {


    navigation(startDestination = SettingScreen.Home.route, route = SETTING_ROUTE) {
        composable(SettingScreen.Home.route) {
         //   HomeScreen({}, {})
        }

    }
}


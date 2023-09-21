package com.app.note_lass.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.note_lass.module.group.GroupScreen

fun NavGraphBuilder.GroupNavGraph(navController: NavController) {


    navigation(startDestination = GroupScreen.Home.route, route = GROUP_ROUTE) {
        composable(GroupScreen.Home.route) {
         GroupScreen()
        }

    }
}


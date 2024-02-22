package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.module.home.HomeScreen
import com.app.note_lass.module.home.NoticeScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.HomeNavGraph(navController: NavController,outerNavController: NavController,role : Role) {


    navigation(startDestination = HomeScreen.Home.route, route = HOME_ROUTE) {
        composable(HomeScreen.Home.route) {
            HomeScreen(navController,
                onClickLogout = {
                outerNavController.navigate(AuthScreen.Login.route){
                    popUpTo(HomeScreen.Home.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

            },
                onClickGroup = {
                    id, info ->
                               if(role == Role.TEACHER){
                                   navController.navigate(GroupScreen.GroupForTeacher.passQuery(id,info))
                               }else{
                                   navController.navigate(GroupScreen.GroupForStudent.passQuery(id,info))
                               }
                },
                onClickGroupAll = {
                    navController.navigate(GroupScreen.Home.route)
                },
                role = role
            )
        }
        composable(HomeScreen.Notice.route) {
            NoticeScreen()
        }

    }
}


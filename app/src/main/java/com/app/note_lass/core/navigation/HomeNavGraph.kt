package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.note_lass.common.DashboardType
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.module.group.ui.DashBoardListScreen
import com.app.note_lass.module.home.DashBoardListInHomeScreen
import com.app.note_lass.module.home.HomeScreen
import com.app.note_lass.module.home.NoticeScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.HomeNavGraph(navController: NavController,outerNavController: NavController,role : Role) {


    navigation(startDestination = HomeScreen.Home.route, route = HOME_ROUTE) {
        composable(HomeScreen.Home.route) {
            HomeScreen(
                onClickLogout = {
                    outerNavController.navigate(AUTH_ROUTE){
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
                role = role,
                onGoToDetail = {
                    dashboardType, id ->
                    if(dashboardType==DashboardType.NOTICE) {
                        navController.navigate(UploadScreen.NoticeDetail.passQuery(id))
                    }else{
                        navController.navigate(UploadScreen.MaterialDetail.passQuery(id))
                    }
                },
                onGoToDashBoard = {
                    navController.navigate(HomeScreen.DashBoard.route)
                }
            )
        }
        composable(HomeScreen.Notice.route) {
            NoticeScreen()
        }
        composable(
            route = HomeScreen.DashBoard.route,
        ) {
            DashBoardListInHomeScreen(
                goToDetailScreen = {
                        dashboardType, id ->
                    if(dashboardType==DashboardType.NOTICE) {
                        navController.navigate(UploadScreen.NoticeDetail.passQuery(id))
                    }else{
                        navController.navigate(UploadScreen.MaterialDetail.passQuery(id))
                    }
                },
                goBack = {
                    navController.popBackStack()
                },
                onClickLogout = {
                    outerNavController.navigate(AUTH_ROUTE){
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}



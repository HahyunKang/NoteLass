package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.app.note_lass.module.main.ui.MainScreen
import com.app.note_lass.module.signup.ui.SignUpScreen
import com.app.note_lass.module.splash.ui.SplashScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = AUTH_ROUTE,
        route = ROOT_ROUTE
    ){
        composable(SPLASH_ROUTE){
            SplashScreen()
        }

        AuthNavGraph(navController = navController)

        composable(route = MAIN_ROUTE){
            MainScreen()
        }
    }
}

package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.note_lass.module.login.ui.LoginScreen
import com.app.note_lass.module.signup.ui.AuthSharedViewModel
import com.app.note_lass.module.signup.ui.SchoolInfoScreen
import com.app.note_lass.module.signup.ui.SignUpScreen
import com.app.note_lass.module.signup.ui.StudentInfoScreen
import com.app.note_lass.module.signup.ui.UpdatePasswordScreen
import com.app.note_lass.module.student.ui.NoticeListScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.AuthNavGraph(navController: NavController) {


    navigation(startDestination = AuthScreen.Login.route, route = AUTH_ROUTE) {
        composable(AuthScreen.Login.route) {
            LoginScreen(onGoToSignUp = {
                navController.navigate(AuthScreen.SchoolInfo.route)
                {
                    launchSingleTop = true
                }
              },
                onGoTOHome = {
                    navController.navigate(MAIN_ROUTE){
                        popUpTo(AuthScreen.Login.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }

                },
                onGoToReset = {
                    navController.navigate(AuthScreen.Reset.route)
                }
            )
        }
        composable(AuthScreen.SchoolInfo.route){
                navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(
                navController = navController)
            SchoolInfoScreen(authSharedViewModel,
                GotoNext = {
                    navController.navigate(AuthScreen.StudentInfo.route)
                    {

                    }
                }
            )
        }
        composable(AuthScreen.StudentInfo.route){
                navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(
                navController = navController)
            StudentInfoScreen(authSharedViewModel,
                GotoNext = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }
        composable(AuthScreen.SignUp.route){
                navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(
                navController = navController)
            SignUpScreen(authSharedViewModel,
                onBack = {
                    navController.popBackStack(AuthScreen.SchoolInfo.route,false)
                },
                GotoLogin ={
                    navController.navigate(AuthScreen.Login.route)

                }
            )
        }

        composable(
            route = AuthScreen.Reset.route,
        ) {
          UpdatePasswordScreen(onBack = {
              navController.popBackStack()
          }
          ) {
              navController.navigate(AuthScreen.Login.route)

          }
        }
    }
}
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.authSharedViewModel(navController: NavController): T {
    // authnavgraph의 상위 NavGraph route를 얻기 위함
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    //navbackstackentry가 바뀌면 코드가 재실행 된다.-> parentEntry 재사용
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    //모든 subgraph의 screen이 끝나야 viewmodel이 destroy된다.
    return hiltViewModel(parentEntry)
}
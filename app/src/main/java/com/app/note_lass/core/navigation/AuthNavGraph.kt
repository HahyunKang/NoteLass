package com.app.note_lass.core.navigation

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

fun NavGraphBuilder.AuthNavGraph(navController: NavController) {


    navigation(startDestination = AuthScreen.Login.route, route = AUTH_ROUTE) {
        composable(AuthScreen.Login.route) {
            LoginScreen(onGoToSignUp = {
                navController.navigate(AuthScreen.SchoolInfo.route)
                {
                    launchSingleTop = true
                }
              }
            )
        }
        composable(AuthScreen.SchoolInfo.route){
                navBackStackEntry ->
            val authSharedViewModel = navBackStackEntry.authSharedViewModel<AuthSharedViewModel>(
                navController = navController)
            SchoolInfoScreen(authSharedViewModel,
                GotoSignUp = {
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
                }
            )
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
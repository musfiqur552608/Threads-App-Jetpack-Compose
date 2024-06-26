package org.freedu.threadsapp.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.freedu.threadsapp.screens.AddThreads
import org.freedu.threadsapp.screens.BottomNav
import org.freedu.threadsapp.screens.Home
import org.freedu.threadsapp.screens.Login
import org.freedu.threadsapp.screens.Notification
import org.freedu.threadsapp.screens.OtherUser
import org.freedu.threadsapp.screens.Profile
import org.freedu.threadsapp.screens.Register
import org.freedu.threadsapp.screens.Search
import org.freedu.threadsapp.screens.Splash

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Splash.routes) {
        composable(Routes.Splash.routes) {
            Splash(navController)
        }
        composable(Routes.Home.routes) {
            Home(navController)
        }
        composable(Routes.Notification.routes) {
            Notification()
        }
        composable(Routes.Search.routes) {
            Search(navController)
        }
        composable(Routes.AddThreads.routes) {
            AddThreads(navController)
        }
        composable(Routes.Profile.routes) {
            Profile(navController)
        }
        composable(Routes.BottomNav.routes) {
            BottomNav(navController)
        }
        composable(Routes.Login.routes) {
            Login(navController)
        }
        composable(Routes.Register.routes) {
            Register(navController)
        }
        composable(Routes.OtherUsers.routes) {
            val data = it.arguments?.getString("data")
            OtherUser(navController, data!!)
        }
    }
}
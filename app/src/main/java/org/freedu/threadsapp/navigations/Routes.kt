package org.freedu.threadsapp.navigations

sealed class Routes(val routes: String) {
    object Home:Routes("home")
    object Splash:Routes("splash")
    object Search:Routes("search")
    object Profile:Routes("profile")
    object Notification:Routes("notification")
    object AddThreads:Routes("addThreads")
    object BottomNav:Routes("bottom_nav")
    object Login:Routes("login")
    object Register:Routes("register")
    object OtherUsers:Routes("other_users/{data}")
}
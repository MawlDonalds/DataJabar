package com.example.hanyarunrun.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object Edit :  Screen("edit/{id}")
    object Profile : Screen("profile")
    object Splash : Screen("splash")
}
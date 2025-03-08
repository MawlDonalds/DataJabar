package com.example.hanyarunrun.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object Edit :  Screen("edit/{id}")
    object Profile : Screen("profile")
    object Splash : Screen("splash")
    object List : Screen("dataList")
    object DetailReward : Screen("home/{rewardId}") {
        fun createRoute(rewardId: Long) = "home/$rewardId"
    }
}
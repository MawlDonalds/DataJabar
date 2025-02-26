package com.example.hanyarunrun

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.hanyarunrun.ui.navigation.NavigationItem
import com.example.hanyarunrun.ui.navigation.Screen
import com.example.hanyarunrun.ui.DataEntryScreen
import com.example.hanyarunrun.ui.DataListScreen
import com.example.hanyarunrun.ui.EditScreen
import com.example.hanyarunrun.ui.ProfileScreen
import com.example.hanyarunrun.ui.SplashScreen
import com.example.hanyarunrun.viewmodel.DataViewModel

@Composable
fun MainScreenApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: DataViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // HIDE BottomBar jika berada di SplashScreen
            if (currentRoute != Screen.Profile.route && currentRoute != Screen.Splash.route) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route, // Mulai dari Splash Screen
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                DataListScreen(viewModel=viewModel, navController = navController)
            }
            composable(Screen.Add.route) {
                DataEntryScreen(viewModel=viewModel, navController = navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(modifier = Modifier)
            }
            composable(Screen.Edit.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                EditScreen(viewModel = viewModel, navController = navController, dataId = id)
            }
        }
    }
}


@Composable
private fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem("Home", Icons.Default.Home, Screen.Home),
        NavigationItem("Add Data", Icons.Default.Add, Screen.Add),
        NavigationItem("Profile", Icons.Default.AccountCircle, Screen.Profile)
    )

    NavigationBar {
        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
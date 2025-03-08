package com.example.hanyarunrun

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.hanyarunrun.ui.navigation.NavigationItem
import com.example.hanyarunrun.ui.navigation.Screen
import com.example.hanyarunrun.ui.AddData.DataEntryScreen
import com.example.hanyarunrun.ui.DataList.DataListScreen
import com.example.hanyarunrun.ui.EditData.EditScreen
import com.example.hanyarunrun.ui.Profile.ProfileScreen
import com.example.hanyarunrun.ui.theme.HanyarunrunTheme
import com.example.hanyarunrun.viewmodel.DataViewModel
import com.example.hanyarunrun.viewmodel.ProfileViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: DataViewModel,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailReward.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentRoute,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth }, // Geser dari kanan
                    animationSpec = tween(500)
                ) with  slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth }, // Geser ke kiri
                    animationSpec = tween(500)
                )
            },
            label = "ScreenTransition"
        ) { targetScreen ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    if (targetScreen == Screen.Home.route) DataListScreen(viewModel = viewModel, navController = navController)
                }
                composable(Screen.Add.route) {
                    if (targetScreen == Screen.Add.route) DataEntryScreen(viewModel = viewModel)
                }
                composable(Screen.Edit.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: 0
                    if (targetScreen == Screen.Edit.route) {
                        EditScreen(navController = navController, viewModel = viewModel, dataId = id)
                    }
                }
//                composable(Screen.List.route) {
//                    if (targetScreen == Screen.List.route) {
//                        DataListScreen(navController = navController, viewModel = viewModel)
//                    }
//                }
                composable(Screen.Profile.route) {
                    if (targetScreen == Screen.Profile.route) ProfileScreen(viewModel = profileViewModel)
                }
            }
        }
    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer, // Warna latar belakang navbar
        contentColor = MaterialTheme.colorScheme.primary // Warna konten di dalam navbar
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem("dataList", Icons.Default.List, Screen.Home),
            NavigationItem("Entry Data", Icons.Default.Add, Screen.Add),
            NavigationItem("Profile", Icons.Default.AccountCircle, Screen.Profile),
        )

        navigationItems.forEach { item ->
            val isSelected = currentRoute == item.screen.route

            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { if (isSelected) Text(item.title) },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
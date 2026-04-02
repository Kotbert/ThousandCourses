package kz.study.example.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kz.study.example.core.ui.theme.Background
import kz.study.example.feature.favorites.FavoritesScreen
import kz.study.example.feature.detail.DetailScreen
import kz.study.example.feature.home.HomeScreen
import kz.study.example.feature.login.LoginScreen
import kz.study.example.feature.profile.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    Scaffold(
        containerColor = Background,
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(currentRoute = currentRoute) { route ->
                    navController.navigate(route) {
                        popUpTo(BottomNavItem.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(BottomNavItem.Home.route) {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    onCourseClick = { courseId ->
                        navController.navigate("detail/$courseId")
                    }
                )
            }
            composable(BottomNavItem.Favorites.route) {
                FavoritesScreen(
                    onCourseClick = { courseId ->
                        navController.navigate("detail/$courseId")
                    }
                )
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(
                    onCourseClick = { courseId ->
                        navController.navigate("detail/$courseId")
                    }
                )
            }
            composable(
                route = "detail/{courseId}",
                arguments = listOf(navArgument("courseId") { type = NavType.IntType })
            ) {
                DetailScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}

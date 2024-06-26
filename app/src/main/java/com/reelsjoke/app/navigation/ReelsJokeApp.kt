package com.reelsjoke.app.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.reelsjoke.app.core.util.SystemBarUtility.SetSystemUIController
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.navigation.screen.createScreen
import com.reelsjoke.app.navigation.screen.detailScreen
import com.reelsjoke.app.navigation.screen.homeScreen
import com.reelsjoke.app.navigation.screen.settingsScreen
import com.reelsjoke.app.theme.AppTheme

/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ReelsJokeApp() {
    AppTheme {
        val navController = rememberNavController()
        val popBackStack: () -> Unit =
            { if (navController.canGoNavigate) navController.popBackStack() }
        val navigateToCreate: () -> Unit = { navController.navigate(Screen.CreateScreen.route) }
        val navigateToDetail: () -> Unit = { navController.navigate(Screen.DetailScreen.route) }
        val navigateHome: (builder: NavOptionsBuilder.() -> Unit) -> Unit =
            { navController.navigate(Screen.HomeScreen.route) }
        val navigateToSettings: () -> Unit =
            { if (navController.canGoNavigate) navController.navigate(Screen.SettingsScreen.route) }
        val setSavedState: (ScreenInfo) -> Unit =
            { navController.currentBackStackEntry?.savedStateHandle?.set("screenInfo", it) }
        val getSavedState: () -> ScreenInfo? =
            { navController.previousBackStackEntry?.savedStateHandle?.get<ScreenInfo>("screenInfo") }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route
        var isDetailScreen by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = currentDestination) {
            currentDestination?.let {
                isDetailScreen = currentDestination.isDetailScreen
            }
        }
        SetSystemUIController(hide = isDetailScreen)

        SharedTransitionLayout {
            NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
                homeScreen(
                    navigateToCreate = navigateToCreate,
                    transitionScope = this@SharedTransitionLayout,
                    navigateToDetail = { screenInfo ->
                        if (navController.canGoNavigate) {
                            setSavedState(screenInfo)
                            navigateToDetail()
                        }
                    },
                    navigateToSettings = navigateToSettings
                )
                createScreen(
                    navigateToDetail = { screenInfo ->
                        popBackStack()
                        setSavedState(screenInfo)
                        navigateToDetail()
                    },
                    navigateToHome = popBackStack
                )
                detailScreen(
                    screenInfo = getSavedState(),
                    transitionScope = this@SharedTransitionLayout,
                    navigateToHome = {
                        navController.takeIf { it.currentBackScreen?.backStackIsCreateScreen == true }
                            ?.let { navigateHome { popUpTo(0) } }
                            ?: run { popBackStack() }
                    }
                )

                settingsScreen(
                    popBackStack = popBackStack
                )
            }
        }
    }
}

val NavHostController.canGoNavigate: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

val String.isDetailScreen: Boolean
    get() = this == Screen.DetailScreen.route

val String.backStackIsCreateScreen: Boolean
    get() = this == Screen.CreateScreen.route

val NavHostController.currentBackScreen: String?
    get() = this.currentBackStackEntry?.destination?.route


sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen("home_screen")
    object CreateScreen : Screen("create_screen")
    object DetailScreen : Screen("detail_screen")
    object SettingsScreen : Screen("settings_screen")
}
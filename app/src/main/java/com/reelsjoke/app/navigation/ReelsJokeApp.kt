package com.reelsjoke.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.reelsjoke.app.core.util.SystemBarUtility.SetSystemUIController
import com.reelsjoke.app.domain.model.ScreenInfo
import com.reelsjoke.app.presentation.create.CreateScreen
import com.reelsjoke.app.presentation.create.CreateScreenViewModel
import com.reelsjoke.app.presentation.detail.DetailScreen
import com.reelsjoke.app.presentation.home.HomeScreen
import com.reelsjoke.app.presentation.home.HomeScreenUIEffect
import com.reelsjoke.app.presentation.home.HomeScreenViewModel
import com.reelsjoke.app.theme.AppTheme

/**
 * Created by bedirhansaricayir on 20.01.2024.
 */

@Composable
fun ReelsJokeApp() {
    AppTheme {
        val navController = rememberNavController()
        val popBackStack: () -> Unit = { navController.popBackStack() }
        val navigateToCreate: () -> Unit = { navController.navigate(Screen.CreateScreen.route) }
        val navigateToDetail: () -> Unit = { navController.navigate(Screen.DetailScreen.route) }
        val setSavedState: (ScreenInfo) -> Unit = { navController.currentBackStackEntry?.savedStateHandle?.set("screenInfo", it) }
        val getSavedState: () -> ScreenInfo? = { navController.previousBackStackEntry?.savedStateHandle?.get<ScreenInfo>("screenInfo") }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route
        var isDetailScreen by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = currentDestination) {
            currentDestination?.let {
                isDetailScreen = currentDestination.isDetailScreen
            }
        }
        SetSystemUIController(hide = isDetailScreen)

        NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
            composable(route = Screen.HomeScreen.route) {
                val viewModel: HomeScreenViewModel = hiltViewModel()
                val homeUIState = viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = Unit) {
                    viewModel.effects.collect { effects ->
                        when(effects) {
                            is HomeScreenUIEffect.NavigateToCreateScreen -> navigateToCreate()
                            is HomeScreenUIEffect.NavigateToDetailScreen -> {
                                if (navController.canGoNavigate) {
                                    setSavedState(effects.item)
                                    navigateToDetail()
                                }
                            }
                        }
                    }
                }
                HomeScreen(
                    homeUIState = homeUIState.value,
                    onEvent = viewModel::onEvent,
                )
            }
            composable(route = Screen.CreateScreen.route) {
                val viewModel: CreateScreenViewModel = hiltViewModel()
                val createUIState = viewModel.state.collectAsStateWithLifecycle()

                CreateScreen(
                    state = createUIState.value,
                    onEvent = viewModel::onEvent,
                    onExportClick = { screenInfo ->
                        popBackStack()
                        setSavedState(screenInfo)
                        navigateToDetail()
                    },
                    navigateUp = {
                        if (navController.canGoNavigate) {
                            popBackStack()
                        }
                    }
                )
            }
            composable(route = Screen.DetailScreen.route) {
                DetailScreen(screenInfo = getSavedState())
            }
        }
    }
}

val NavHostController.canGoNavigate: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

val String.isDetailScreen: Boolean
    get() = this == Screen.DetailScreen.route


sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen("home_screen")
    object CreateScreen : Screen("create_screen")
    object DetailScreen : Screen("detail_screen")
}
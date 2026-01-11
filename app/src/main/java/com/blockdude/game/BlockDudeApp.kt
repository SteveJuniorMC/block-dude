package com.blockdude.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blockdude.game.ui.screens.GameScreen
import com.blockdude.game.ui.screens.LevelSelectScreen
import com.blockdude.game.ui.screens.MainMenuScreen
import com.blockdude.game.ui.theme.BlockDudeTheme
import com.blockdude.game.ui.viewmodel.GameViewModel

sealed class Screen(val route: String) {
    data object MainMenu : Screen("main_menu")
    data object LevelSelect : Screen("level_select")
    data object Game : Screen("game")
}

@Composable
fun BlockDudeApp(
    navController: NavHostController = rememberNavController(),
    viewModel: GameViewModel = viewModel()
) {
    val completedLevels by viewModel.completedLevels.collectAsState()
    val currentLevel by viewModel.currentLevel.collectAsState()
    val gameState by viewModel.gameState.collectAsState()

    BlockDudeTheme {
        NavHost(
            navController = navController,
            startDestination = Screen.MainMenu.route
        ) {
            composable(Screen.MainMenu.route) {
                MainMenuScreen(
                    onPlayClick = {
                        viewModel.startNextAvailableLevel()
                        navController.navigate(Screen.Game.route)
                    },
                    onLevelSelectClick = {
                        navController.navigate(Screen.LevelSelect.route)
                    }
                )
            }

            composable(Screen.LevelSelect.route) {
                LevelSelectScreen(
                    levels = viewModel.levels,
                    completedLevels = completedLevels,
                    onLevelClick = { levelId ->
                        viewModel.startLevel(levelId)
                        navController.navigate(Screen.Game.route)
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Game.route) {
                val level = currentLevel
                val state = gameState
                val engine = viewModel.getGameEngine()

                if (level != null && state != null && engine != null) {
                    GameScreen(
                        level = level,
                        gameState = state,
                        gameEngine = engine,
                        onMoveLeft = viewModel::moveLeft,
                        onMoveRight = viewModel::moveRight,
                        onMoveUp = viewModel::moveUp,
                        onAction = viewModel::pickUpOrPlace,
                        onRestart = viewModel::restartLevel,
                        onBack = {
                            navController.navigate(Screen.LevelSelect.route) {
                                popUpTo(Screen.MainMenu.route)
                            }
                        },
                        onNextLevel = {
                            viewModel.goToNextLevel()
                        },
                        hasNextLevel = viewModel.hasNextLevel()
                    )
                }
            }
        }
    }
}

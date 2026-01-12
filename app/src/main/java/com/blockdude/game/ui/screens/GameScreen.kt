package com.blockdude.game.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockdude.game.data.GameState
import com.blockdude.game.data.Level
import com.blockdude.game.game.GameEngine
import com.blockdude.game.ui.components.*
import com.blockdude.game.ui.theme.*

@Composable
fun GameScreen(
    level: Level,
    gameState: GameState,
    gameEngine: GameEngine,
    onMoveLeft: () -> Unit,
    onMoveRight: () -> Unit,
    onMoveUp: () -> Unit,
    onAction: () -> Unit,
    onRestart: () -> Unit,
    onBack: () -> Unit,
    onNextLevel: () -> Unit,
    hasNextLevel: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // HUD
            GameHUD(
                levelNumber = level.id,
                levelName = level.name,
                moves = gameState.moves,
                onRestart = onRestart,
                onBack = onBack
            )

            // Game area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                GameCanvas(
                    level = level,
                    gameState = gameState,
                    gameEngine = gameEngine
                )
            }

            // Controls
            GameControls(
                onMoveLeft = onMoveLeft,
                onMoveRight = onMoveRight,
                onMoveUp = onMoveUp,
                onAction = onAction,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Level complete overlay
        AnimatedVisibility(
            visible = gameState.levelCompleted,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            LevelCompleteOverlay(
                moves = gameState.moves,
                onRestart = onRestart,
                onNextLevel = onNextLevel,
                onBack = onBack,
                hasNextLevel = hasNextLevel
            )
        }
    }
}

@Composable
private fun LevelCompleteOverlay(
    moves: Int,
    onRestart: () -> Unit,
    onNextLevel: () -> Unit,
    onBack: () -> Unit,
    hasNextLevel: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f)),
        contentAlignment = Alignment.Center
    ) {
        // Brick background panel with pixel frame
        BrickBackground(
            brickColor = WallColor,
            mortarColor = UIMortarColor
        ) {
            PixelFrame(
                borderColor = BlockColor,
                backgroundColor = Color.Transparent,
                borderWidth = 6.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text(
                        text = "LEVEL",
                        color = TextWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "COMPLETE!",
                        color = CompletedColor,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 4.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Moves in pixel frame
                    PixelFrame(
                        borderColor = WallColor,
                        backgroundColor = GroundColor,
                        borderWidth = 3.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "MOVES",
                                color = TextWhite.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                            Text(
                                text = "$moves",
                                color = AccentOrange,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Buttons
                    if (hasNextLevel) {
                        WoodButton(
                            text = "NEXT LEVEL",
                            onClick = onNextLevel,
                            width = 180.dp,
                            height = 48.dp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    WoodButton(
                        text = "RETRY",
                        onClick = onRestart,
                        width = 180.dp,
                        height = 48.dp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    WoodButton(
                        text = "LEVELS",
                        onClick = onBack,
                        width = 180.dp,
                        height = 48.dp
                    )
                }
            }
        }
    }
}

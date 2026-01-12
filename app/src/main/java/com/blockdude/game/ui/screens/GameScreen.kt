package com.blockdude.game.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockdude.game.data.GameState
import com.blockdude.game.data.Level
import com.blockdude.game.game.GameEngine
import com.blockdude.game.ui.components.GameCanvas
import com.blockdude.game.ui.components.GameControls
import com.blockdude.game.ui.components.GameHUD
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
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // HUD
            GameHUD(
                levelNumber = level.id,
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
                modifier = Modifier.padding(bottom = 32.dp)
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
            .background(Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceColor)
                .padding(32.dp)
        ) {
            Text(
                text = "LEVEL",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "COMPLETE!",
                color = CompletedColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Moves: $moves",
                color = AccentOrange,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Buttons
            if (hasNextLevel) {
                OverlayButton(
                    text = "NEXT",
                    color = CompletedColor,
                    onClick = onNextLevel
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            OverlayButton(
                text = "RETRY",
                color = AccentOrange,
                onClick = onRestart
            )

            Spacer(modifier = Modifier.height(8.dp))

            OverlayButton(
                text = "LEVELS",
                color = PrimaryBlue,
                onClick = onBack
            )
        }
    }
}

@Composable
private fun OverlayButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

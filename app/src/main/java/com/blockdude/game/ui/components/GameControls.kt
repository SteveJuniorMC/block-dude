package com.blockdude.game.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.blockdude.game.ui.theme.AccentOrange
import com.blockdude.game.ui.theme.PrimaryBlue
import com.blockdude.game.ui.theme.SurfaceColor

@Composable
fun GameControls(
    onMoveLeft: () -> Unit,
    onMoveRight: () -> Unit,
    onMoveUp: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // D-Pad layout
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Up button
            ControlButton(
                onClick = onMoveUp,
                color = PrimaryBlue
            ) {
                Text(
                    text = "^",
                    color = Color.White,
                    modifier = Modifier.offset(y = 2.dp)
                )
            }

            // Left and Right buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ControlButton(
                    onClick = onMoveLeft,
                    color = PrimaryBlue
                ) {
                    Text(
                        text = "<",
                        color = Color.White,
                        modifier = Modifier.offset(x = (-2).dp)
                    )
                }

                ControlButton(
                    onClick = onMoveRight,
                    color = PrimaryBlue
                ) {
                    Text(
                        text = ">",
                        color = Color.White,
                        modifier = Modifier.offset(x = 2.dp)
                    )
                }
            }

            // Down button (action - pick up / place)
            ControlButton(
                onClick = onAction,
                color = AccentOrange
            ) {
                Text(
                    text = "v",
                    color = Color.White,
                    modifier = Modifier.offset(y = (-2).dp)
                )
            }
        }
    }
}

@Composable
private fun ControlButton(
    onClick: () -> Unit,
    color: Color,
    size: androidx.compose.ui.unit.Dp = 64.dp,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(if (isPressed) color.copy(alpha = 0.7f) else color)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun GameHUD(
    levelNumber: Int,
    levelName: String,
    moves: Int,
    onRestart: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(PrimaryBlue)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "<", color = Color.White)
        }

        // Level info
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Level $levelNumber",
                color = Color.White
            )
            Text(
                text = levelName,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        // Moves counter
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Moves", color = Color.White.copy(alpha = 0.7f))
            Text(text = "$moves", color = AccentOrange)
        }

        // Restart button
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AccentOrange)
                .clickable(onClick = onRestart),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "R", color = Color.White)
        }
    }
}

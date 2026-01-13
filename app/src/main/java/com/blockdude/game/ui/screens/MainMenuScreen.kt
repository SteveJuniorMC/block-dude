package com.blockdude.game.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockdude.game.ui.theme.*

@Composable
fun MainMenuScreen(
    onPlayClick: () -> Unit,
    onLevelSelectClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "BLOCK",
            color = PlayerColor,
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 8.sp
        )
        Text(
            text = "DUDE",
            color = AccentOrange,
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 8.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Subtitle
        Text(
            text = "A Classic Puzzle Game",
            color = TextWhite.copy(alpha = 0.6f),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(80.dp))

        // Play button
        MenuButton(
            text = "PLAY",
            color = AccentOrange,
            onClick = onPlayClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Level Select button
        MenuButton(
            text = "LEVELS",
            color = PrimaryBlue,
            onClick = onLevelSelectClick
        )

        Spacer(modifier = Modifier.height(80.dp))

        // Instructions
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                text = "HOW TO PLAY",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Move left/right
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MiniButton(color = PrimaryBlue) { MiniArrow(Direction.LEFT) }
                MiniButton(color = PrimaryBlue) { MiniArrow(Direction.RIGHT) }
                Text(
                    text = "Move",
                    color = TextWhite.copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Climb up
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MiniButton(color = PrimaryBlue) { MiniArrow(Direction.UP) }
                Text(
                    text = "Climb",
                    color = TextWhite.copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Pick up / place
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MiniButton(color = AccentOrange) { MiniActionIcon() }
                Text(
                    text = "Pick up / Place",
                    color = TextWhite.copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Reach the door!",
                color = DoorColor,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun MenuButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    }
}

private enum class Direction { UP, LEFT, RIGHT }

@Composable
private fun MiniButton(
    color: Color,
    size: Dp = 28.dp,
    content: @Composable () -> Unit
) {
    val darkColor = Color(
        red = (color.red * 0.6f).coerceIn(0f, 1f),
        green = (color.green * 0.6f).coerceIn(0f, 1f),
        blue = (color.blue * 0.6f).coerceIn(0f, 1f)
    )

    Box(contentAlignment = Alignment.Center) {
        // Shadow
        Box(
            modifier = Modifier
                .size(size)
                .offset(y = 2.dp)
                .clip(CircleShape)
                .background(darkColor)
        )
        // Button face
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
private fun MiniArrow(direction: Direction) {
    Canvas(modifier = Modifier.size(14.dp)) {
        val path = Path()
        val w = size.width
        val h = size.height
        val padding = w * 0.15f

        when (direction) {
            Direction.UP -> {
                path.moveTo(w / 2, padding)
                path.lineTo(w - padding, h - padding)
                path.lineTo(padding, h - padding)
                path.close()
            }
            Direction.LEFT -> {
                path.moveTo(w - padding, padding)
                path.lineTo(w - padding, h - padding)
                path.lineTo(padding, h / 2)
                path.close()
            }
            Direction.RIGHT -> {
                path.moveTo(padding, padding)
                path.lineTo(w - padding, h / 2)
                path.lineTo(padding, h - padding)
                path.close()
            }
        }

        drawPath(path, Color.White, style = Fill)
    }
}

@Composable
private fun MiniActionIcon() {
    Canvas(modifier = Modifier.size(14.dp)) {
        val w = size.width
        val h = size.height
        val padding = w * 0.2f
        val barHeight = h * 0.15f

        // Draw grab icon
        drawRect(
            color = Color.White,
            topLeft = androidx.compose.ui.geometry.Offset(padding, padding),
            size = androidx.compose.ui.geometry.Size(w - padding * 2, barHeight)
        )
        drawRect(
            color = Color.White,
            topLeft = androidx.compose.ui.geometry.Offset(padding, padding),
            size = androidx.compose.ui.geometry.Size(barHeight, h - padding * 2)
        )
        drawRect(
            color = Color.White,
            topLeft = androidx.compose.ui.geometry.Offset(w - padding - barHeight, padding),
            size = androidx.compose.ui.geometry.Size(barHeight, h - padding * 2)
        )
        drawRect(
            color = Color.White,
            topLeft = androidx.compose.ui.geometry.Offset(padding, h - padding - barHeight),
            size = androidx.compose.ui.geometry.Size(w - padding * 2, barHeight)
        )
    }
}

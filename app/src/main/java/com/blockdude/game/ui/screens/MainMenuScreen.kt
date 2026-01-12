package com.blockdude.game.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "◀ ▶  Move left/right",
                color = TextWhite.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
            Text(
                text = "▲  Climb up",
                color = TextWhite.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
            Text(
                text = "▼  Pick up / place block",
                color = TextWhite.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
            Text(
                text = "Reach the door to win!",
                color = TextWhite.copy(alpha = 0.6f),
                fontSize = 12.sp
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

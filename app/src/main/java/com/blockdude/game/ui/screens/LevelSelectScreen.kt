package com.blockdude.game.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.blockdude.game.data.Level
import com.blockdude.game.ui.components.LevelCard
import com.blockdude.game.ui.components.LevelStatus
import com.blockdude.game.ui.theme.*

@Composable
fun LevelSelectScreen(
    levels: List<Level>,
    completedLevels: Set<Int>,
    onLevelClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceColor)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryBlue)
                    .clickable(onClick = onBackClick),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "<", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "SELECT LEVEL",
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }

        // Progress info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Completed: ${completedLevels.size}/${levels.size}",
                color = CompletedColor,
                fontSize = 14.sp
            )
            Text(
                text = "Tap to play",
                color = TextWhite.copy(alpha = 0.5f),
                fontSize = 14.sp
            )
        }

        // Level grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(levels) { level ->
                // All levels unlocked for testing
                val status = when {
                    completedLevels.contains(level.id) -> LevelStatus.COMPLETED
                    else -> LevelStatus.UNLOCKED
                }

                LevelCard(
                    levelNumber = level.id,
                    levelName = level.name,
                    status = status,
                    onClick = { onLevelClick(level.id) }
                )
            }
        }
    }
}

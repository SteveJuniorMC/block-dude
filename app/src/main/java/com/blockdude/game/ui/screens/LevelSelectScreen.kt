package com.blockdude.game.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockdude.game.data.Level
import com.blockdude.game.ui.components.BrickBackground
import com.blockdude.game.ui.components.LevelCard
import com.blockdude.game.ui.components.LevelStatus
import com.blockdude.game.ui.components.WoodIconButton
import com.blockdude.game.ui.theme.*

@Composable
fun LevelSelectScreen(
    levels: List<Level>,
    completedLevels: Set<Int>,
    onLevelClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    BrickBackground(
        modifier = Modifier.fillMaxSize(),
        brickColor = WallColor,
        mortarColor = UIMortarColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with brick background
            BrickBackground(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                brickColor = UIBrickColor,
                mortarColor = UIMortarColor
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WoodIconButton(
                        onClick = onBackClick,
                        size = 44.dp
                    ) {
                        // Back arrow icon
                        Canvas(modifier = Modifier.size(20.dp)) {
                            val path = Path().apply {
                                moveTo(size.width * 0.7f, size.height * 0.2f)
                                lineTo(size.width * 0.3f, size.height * 0.5f)
                                lineTo(size.width * 0.7f, size.height * 0.8f)
                                close()
                            }
                            drawPath(path, Color.White, style = Fill)
                        }
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
            }

            // Progress info bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Completed: ${completedLevels.size}/${levels.size}",
                    color = CompletedColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tap to play",
                    color = TextWhite.copy(alpha = 0.6f),
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
}

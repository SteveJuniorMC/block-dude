package com.blockdude.game.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockdude.game.ui.theme.*

enum class LevelStatus {
    LOCKED,
    UNLOCKED,
    COMPLETED
}

@Composable
fun LevelCard(
    levelNumber: Int,
    levelName: String,
    status: LevelStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status) {
        LevelStatus.LOCKED -> GroundColor.copy(alpha = 0.5f)
        LevelStatus.UNLOCKED -> GroundColor
        LevelStatus.COMPLETED -> GroundColor
    }

    val borderColor = when (status) {
        LevelStatus.LOCKED -> LockedColor
        LevelStatus.UNLOCKED -> BlockColor
        LevelStatus.COMPLETED -> CompletedColor
    }

    val textColor = when (status) {
        LevelStatus.LOCKED -> LockedColor
        LevelStatus.UNLOCKED -> TextWhite
        LevelStatus.COMPLETED -> CompletedColor
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .then(
                if (status != LevelStatus.LOCKED) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        // Draw pixel-style card background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val borderWidth = 3.dp.toPx()

            // Background
            drawRect(
                color = backgroundColor,
                topLeft = Offset(borderWidth, borderWidth),
                size = Size(size.width - borderWidth * 2, size.height - borderWidth * 2)
            )

            // Pixel-style border (hard edges)
            drawRect(
                color = borderColor,
                topLeft = Offset.Zero,
                size = size,
                style = Stroke(width = borderWidth)
            )

            // Inner highlight/shadow for 3D effect
            val highlightColor = borderColor.copy(alpha = 0.5f)
            val shadowColor = Color(0xFF1A1A1A)

            // Top highlight
            drawLine(
                color = highlightColor,
                start = Offset(borderWidth, borderWidth),
                end = Offset(size.width - borderWidth, borderWidth),
                strokeWidth = 2.dp.toPx()
            )
            // Left highlight
            drawLine(
                color = highlightColor,
                start = Offset(borderWidth, borderWidth),
                end = Offset(borderWidth, size.height - borderWidth),
                strokeWidth = 2.dp.toPx()
            )
            // Bottom shadow
            drawLine(
                color = shadowColor,
                start = Offset(borderWidth, size.height - borderWidth),
                end = Offset(size.width - borderWidth, size.height - borderWidth),
                strokeWidth = 2.dp.toPx()
            )
            // Right shadow
            drawLine(
                color = shadowColor,
                start = Offset(size.width - borderWidth, borderWidth),
                end = Offset(size.width - borderWidth, size.height - borderWidth),
                strokeWidth = 2.dp.toPx()
            )

            // For completed levels, draw a checkmark
            if (status == LevelStatus.COMPLETED) {
                val checkSize = size.width * 0.15f
                val checkX = size.width - checkSize - 8.dp.toPx()
                val checkY = 8.dp.toPx()

                // Checkmark
                drawLine(
                    color = CompletedColor,
                    start = Offset(checkX, checkY + checkSize * 0.5f),
                    end = Offset(checkX + checkSize * 0.4f, checkY + checkSize),
                    strokeWidth = 3.dp.toPx()
                )
                drawLine(
                    color = CompletedColor,
                    start = Offset(checkX + checkSize * 0.4f, checkY + checkSize),
                    end = Offset(checkX + checkSize, checkY),
                    strokeWidth = 3.dp.toPx()
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (status == LevelStatus.LOCKED) "?" else "$levelNumber",
                color = textColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            if (status != LevelStatus.LOCKED) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = levelName,
                    color = textColor.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

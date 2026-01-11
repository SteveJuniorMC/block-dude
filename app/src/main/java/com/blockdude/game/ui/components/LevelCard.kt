package com.blockdude.game.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
        LevelStatus.LOCKED -> LockedColor.copy(alpha = 0.3f)
        LevelStatus.UNLOCKED -> SurfaceColor
        LevelStatus.COMPLETED -> CompletedColor.copy(alpha = 0.2f)
    }

    val borderColor = when (status) {
        LevelStatus.LOCKED -> LockedColor
        LevelStatus.UNLOCKED -> UnlockedColor
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
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .then(
                if (status != LevelStatus.LOCKED) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
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

            if (status == LevelStatus.COMPLETED) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "DONE",
                    color = CompletedColor,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

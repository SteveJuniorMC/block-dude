package com.blockdude.game.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockdude.game.ui.theme.*

@Composable
fun GameControls(
    onMoveLeft: () -> Unit,
    onMoveRight: () -> Unit,
    onMoveUp: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // D-Pad on the left
        DPad(
            onUp = onMoveUp,
            onDown = onAction,  // Down also triggers action
            onLeft = onMoveLeft,
            onRight = onMoveRight,
            size = 160.dp
        )

        // Action button on the right
        ActionButton(
            onClick = onAction,
            size = 90.dp
        )
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
    BrickBackground(
        modifier = modifier.fillMaxWidth(),
        brickColor = UIBrickColor,
        mortarColor = UIMortarColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            WoodIconButton(
                onClick = onBack,
                size = 40.dp
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    val path = Path().apply {
                        moveTo(size.width * 0.7f, size.height * 0.2f)
                        lineTo(size.width * 0.3f, size.height * 0.5f)
                        lineTo(size.width * 0.7f, size.height * 0.8f)
                        close()
                    }
                    drawPath(path, Color.White, style = Fill)
                }
            }

            // Level info
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Level $levelNumber",
                    color = TextWhite,
                    fontSize = 14.sp
                )
                Text(
                    text = levelName,
                    color = TextWhite.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }

            // Moves counter in pixel frame style
            PixelFrame(
                borderColor = WallColor,
                backgroundColor = GroundColor,
                borderWidth = 2.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "MOVES",
                        color = TextWhite.copy(alpha = 0.7f),
                        fontSize = 10.sp
                    )
                    Text(
                        text = "$moves",
                        color = AccentOrange,
                        fontSize = 18.sp
                    )
                }
            }

            // Restart button
            WoodIconButton(
                onClick = onRestart,
                size = 40.dp
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    val w = size.width
                    val h = size.height
                    val strokeWidth = w * 0.15f

                    // Circular arrow
                    drawArc(
                        color = Color.White,
                        startAngle = -60f,
                        sweepAngle = 300f,
                        useCenter = false,
                        topLeft = Offset(strokeWidth, strokeWidth),
                        size = androidx.compose.ui.geometry.Size(w - strokeWidth * 2, h - strokeWidth * 2),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                    )

                    // Arrow head
                    val path = Path().apply {
                        val arrowSize = w * 0.3f
                        moveTo(w * 0.65f, strokeWidth * 0.5f)
                        lineTo(w * 0.65f + arrowSize, strokeWidth * 1.5f)
                        lineTo(w * 0.65f, strokeWidth * 2.5f)
                        close()
                    }
                    drawPath(path, Color.White, style = Fill)
                }
            }
        }
    }
}

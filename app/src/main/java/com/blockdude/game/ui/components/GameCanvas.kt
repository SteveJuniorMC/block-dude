package com.blockdude.game.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import com.blockdude.game.data.GameState
import com.blockdude.game.data.Level
import com.blockdude.game.game.CellInfo
import com.blockdude.game.game.Direction
import com.blockdude.game.game.GameEngine
import com.blockdude.game.ui.theme.*

@Composable
fun GameCanvas(
    level: Level,
    gameState: GameState,
    gameEngine: GameEngine,
    modifier: Modifier = Modifier
) {
    val animatedPlayerX = remember { Animatable(gameState.playerPosition.x.toFloat()) }
    val animatedPlayerY = remember { Animatable(gameState.playerPosition.y.toFloat()) }

    LaunchedEffect(gameState.playerPosition) {
        animatedPlayerX.animateTo(
            targetValue = gameState.playerPosition.x.toFloat(),
            animationSpec = tween(100)
        )
    }

    LaunchedEffect(gameState.playerPosition) {
        animatedPlayerY.animateTo(
            targetValue = gameState.playerPosition.y.toFloat(),
            animationSpec = tween(100)
        )
    }

    val aspectRatio = level.width.toFloat() / level.height.toFloat()

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
    ) {
        val cellWidth = size.width / level.width
        val cellHeight = size.height / level.height
        val cellSize = minOf(cellWidth, cellHeight)

        val offsetX = (size.width - cellSize * level.width) / 2
        val offsetY = (size.height - cellSize * level.height) / 2

        // Draw background
        drawRect(
            color = GroundColor,
            topLeft = Offset(offsetX, offsetY),
            size = Size(cellSize * level.width, cellSize * level.height)
        )

        // Draw all cells
        for (y in 0 until level.height) {
            for (x in 0 until level.width) {
                val cellInfo = gameEngine.getCellAt(x, y, gameState)
                val cellX = offsetX + x * cellSize
                val cellY = offsetY + y * cellSize

                when (cellInfo) {
                    is CellInfo.Wall -> drawWall(cellX, cellY, cellSize)
                    is CellInfo.Block -> drawBlock(cellX, cellY, cellSize)
                    is CellInfo.Door -> drawDoor(cellX, cellY, cellSize)
                    is CellInfo.Empty -> {} // Already have background
                    is CellInfo.Player -> {} // Draw separately for animation
                }
            }
        }

        // Draw player with animation
        val playerX = offsetX + animatedPlayerX.value * cellSize
        val playerY = offsetY + animatedPlayerY.value * cellSize
        drawPlayer(playerX, playerY, cellSize, gameState.playerFacing, gameState.holdingBlock)

        // Draw door on top if player is at door (for win effect)
        if (gameState.levelCompleted) {
            val doorX = offsetX + level.doorPosition.x * cellSize
            val doorY = offsetY + level.doorPosition.y * cellSize
            drawDoorOpen(doorX, doorY, cellSize)
        }
    }
}

private fun DrawScope.drawWall(x: Float, y: Float, size: Float) {
    // Main wall block
    drawRect(
        color = WallColor,
        topLeft = Offset(x, y),
        size = Size(size, size)
    )
    // Brick pattern
    val brickHeight = size / 3
    val brickWidth = size / 2

    for (row in 0 until 3) {
        val offset = if (row % 2 == 0) 0f else brickWidth / 2
        drawLine(
            color = Color(0xFF3A3A3A),
            start = Offset(x, y + row * brickHeight),
            end = Offset(x + size, y + row * brickHeight),
            strokeWidth = 1f
        )
    }
    drawLine(
        color = Color(0xFF3A3A3A),
        start = Offset(x + brickWidth, y),
        end = Offset(x + brickWidth, y + brickHeight),
        strokeWidth = 1f
    )
    drawLine(
        color = Color(0xFF3A3A3A),
        start = Offset(x + brickWidth / 2, y + brickHeight),
        end = Offset(x + brickWidth / 2, y + 2 * brickHeight),
        strokeWidth = 1f
    )
    drawLine(
        color = Color(0xFF3A3A3A),
        start = Offset(x + brickWidth, y + 2 * brickHeight),
        end = Offset(x + brickWidth, y + size),
        strokeWidth = 1f
    )
}

private fun DrawScope.drawBlock(x: Float, y: Float, size: Float) {
    val padding = size * 0.05f

    // Shadow
    drawRect(
        color = BlockDark,
        topLeft = Offset(x + padding, y + padding),
        size = Size(size - padding * 2, size - padding * 2)
    )

    // Main block
    drawRect(
        color = BlockColor,
        topLeft = Offset(x + padding, y + padding),
        size = Size(size - padding * 3, size - padding * 3)
    )

    // Highlight
    drawLine(
        color = Color(0xFFE8C9A0),
        start = Offset(x + padding * 2, y + padding * 2),
        end = Offset(x + size - padding * 4, y + padding * 2),
        strokeWidth = 2f
    )
}

private fun DrawScope.drawDoor(x: Float, y: Float, size: Float) {
    val padding = size * 0.1f

    // Door frame
    drawRect(
        color = DoorFrame,
        topLeft = Offset(x + padding, y),
        size = Size(size - padding * 2, size)
    )

    // Door
    drawRect(
        color = DoorColor,
        topLeft = Offset(x + padding * 2, y + padding),
        size = Size(size - padding * 4, size - padding)
    )

    // Door handle
    drawCircle(
        color = Color(0xFF5A9E6A),
        radius = size * 0.08f,
        center = Offset(x + size * 0.7f, y + size * 0.5f)
    )
}

private fun DrawScope.drawDoorOpen(x: Float, y: Float, size: Float) {
    val padding = size * 0.1f

    // Door frame
    drawRect(
        color = DoorFrame,
        topLeft = Offset(x + padding, y),
        size = Size(size - padding * 2, size)
    )

    // Open door (dark inside)
    drawRect(
        color = Color(0xFF1A3A1A),
        topLeft = Offset(x + padding * 2, y + padding),
        size = Size(size - padding * 4, size - padding)
    )
}

private fun DrawScope.drawPlayer(x: Float, y: Float, size: Float, facing: Direction, holdingBlock: Boolean) {
    val padding = size * 0.15f
    val bodyWidth = size - padding * 2
    val bodyHeight = size * 0.5f
    val headSize = size * 0.35f
    val legWidth = size * 0.15f

    // Legs
    drawRect(
        color = PlayerDark,
        topLeft = Offset(x + padding + bodyWidth * 0.1f, y + size * 0.7f),
        size = Size(legWidth, size * 0.3f)
    )
    drawRect(
        color = PlayerDark,
        topLeft = Offset(x + padding + bodyWidth * 0.6f, y + size * 0.7f),
        size = Size(legWidth, size * 0.3f)
    )

    // Body
    drawRect(
        color = PlayerColor,
        topLeft = Offset(x + padding, y + size * 0.35f),
        size = Size(bodyWidth, bodyHeight)
    )

    // Head
    drawCircle(
        color = PlayerColor,
        radius = headSize / 2,
        center = Offset(x + size / 2, y + size * 0.25f)
    )

    // Eyes
    val eyeOffset = if (facing == Direction.LEFT) -headSize * 0.15f else headSize * 0.15f
    drawCircle(
        color = Color.White,
        radius = headSize * 0.12f,
        center = Offset(x + size / 2 + eyeOffset, y + size * 0.22f)
    )
    drawCircle(
        color = Color.Black,
        radius = headSize * 0.06f,
        center = Offset(x + size / 2 + eyeOffset + (if (facing == Direction.LEFT) -2f else 2f), y + size * 0.22f)
    )

    // Arm pointing in facing direction
    val armStartX = if (facing == Direction.LEFT) x + padding else x + size - padding
    val armEndX = if (facing == Direction.LEFT) x else x + size
    drawLine(
        color = PlayerDark,
        start = Offset(armStartX, y + size * 0.45f),
        end = Offset(armEndX, y + size * 0.4f),
        strokeWidth = size * 0.1f
    )

    // Holding block
    if (holdingBlock) {
        val blockX = x + size / 2 - size * 0.2f
        val blockY = y - size * 0.3f
        val blockSize = size * 0.4f

        drawRect(
            color = BlockDark,
            topLeft = Offset(blockX, blockY),
            size = Size(blockSize, blockSize)
        )
        drawRect(
            color = BlockColor,
            topLeft = Offset(blockX, blockY),
            size = Size(blockSize * 0.9f, blockSize * 0.9f)
        )
    }
}

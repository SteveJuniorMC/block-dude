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
    // Main wall block with base color
    drawRect(
        color = WallColor,
        topLeft = Offset(x, y),
        size = Size(size, size)
    )

    // Brick pattern - 3 rows of bricks
    val brickHeight = size / 3
    val brickWidth = size / 2
    val mortarColor = Color(0xFF2A2A2A)
    val highlightColor = Color(0xFF6A6A6A)
    val shadowColor = Color(0xFF1A1A1A)
    val mortarWidth = size * 0.06f

    // Draw individual bricks with highlights and shadows
    for (row in 0 until 3) {
        val offsetX = if (row % 2 == 0) 0f else brickWidth / 2
        val brickY = y + row * brickHeight

        // Draw bricks in this row
        var brickX = x + offsetX - brickWidth
        while (brickX < x + size) {
            val drawX = maxOf(brickX, x)
            val drawWidth = minOf(brickX + brickWidth, x + size) - drawX
            if (drawWidth > 0) {
                val brickLeft = drawX + mortarWidth / 2
                val brickTop = brickY + mortarWidth / 2
                val brickW = drawWidth - mortarWidth
                val brickH = brickHeight - mortarWidth

                if (brickW > 0 && brickH > 0) {
                    // Brick highlight (top-left edges)
                    drawLine(
                        color = highlightColor,
                        start = Offset(brickLeft, brickTop),
                        end = Offset(brickLeft + brickW, brickTop),
                        strokeWidth = size * 0.04f
                    )
                    drawLine(
                        color = highlightColor,
                        start = Offset(brickLeft, brickTop),
                        end = Offset(brickLeft, brickTop + brickH),
                        strokeWidth = size * 0.04f
                    )

                    // Brick shadow (bottom-right edges)
                    drawLine(
                        color = shadowColor,
                        start = Offset(brickLeft, brickTop + brickH),
                        end = Offset(brickLeft + brickW, brickTop + brickH),
                        strokeWidth = size * 0.04f
                    )
                    drawLine(
                        color = shadowColor,
                        start = Offset(brickLeft + brickW, brickTop),
                        end = Offset(brickLeft + brickW, brickTop + brickH),
                        strokeWidth = size * 0.04f
                    )
                }
            }
            brickX += brickWidth
        }
    }

    // Draw mortar lines
    // Horizontal mortar lines
    for (row in 1 until 3) {
        drawLine(
            color = mortarColor,
            start = Offset(x, y + row * brickHeight),
            end = Offset(x + size, y + row * brickHeight),
            strokeWidth = mortarWidth
        )
    }

    // Vertical mortar lines (staggered)
    drawLine(
        color = mortarColor,
        start = Offset(x + brickWidth, y),
        end = Offset(x + brickWidth, y + brickHeight),
        strokeWidth = mortarWidth
    )
    drawLine(
        color = mortarColor,
        start = Offset(x + brickWidth / 2, y + brickHeight),
        end = Offset(x + brickWidth / 2, y + 2 * brickHeight),
        strokeWidth = mortarWidth
    )
    drawLine(
        color = mortarColor,
        start = Offset(x + brickWidth, y + 2 * brickHeight),
        end = Offset(x + brickWidth, y + size),
        strokeWidth = mortarWidth
    )
}

private fun DrawScope.drawBlock(x: Float, y: Float, size: Float) {
    val padding = size * 0.05f
    val shadowOffset = size * 0.08f

    // Drop shadow
    drawRect(
        color = Color(0xFF1A1A1A),
        topLeft = Offset(x + shadowOffset, y + shadowOffset),
        size = Size(size - padding, size - padding)
    )

    // Main block base (darker wood)
    drawRect(
        color = BlockDark,
        topLeft = Offset(x + padding, y + padding),
        size = Size(size - padding * 2, size - padding * 2)
    )

    // Main block face
    drawRect(
        color = BlockColor,
        topLeft = Offset(x + padding, y + padding),
        size = Size(size - padding * 2 - shadowOffset, size - padding * 2 - shadowOffset)
    )

    // Wood grain lines
    val grainColor = Color(0xFFB8956A)
    val grainWidth = size * 0.02f
    val blockLeft = x + padding
    val blockTop = y + padding
    val blockSize = size - padding * 2 - shadowOffset

    // Horizontal wood grain
    drawLine(
        color = grainColor,
        start = Offset(blockLeft + blockSize * 0.1f, blockTop + blockSize * 0.3f),
        end = Offset(blockLeft + blockSize * 0.9f, blockTop + blockSize * 0.3f),
        strokeWidth = grainWidth
    )
    drawLine(
        color = grainColor,
        start = Offset(blockLeft + blockSize * 0.15f, blockTop + blockSize * 0.5f),
        end = Offset(blockLeft + blockSize * 0.85f, blockTop + blockSize * 0.5f),
        strokeWidth = grainWidth
    )
    drawLine(
        color = grainColor,
        start = Offset(blockLeft + blockSize * 0.1f, blockTop + blockSize * 0.7f),
        end = Offset(blockLeft + blockSize * 0.9f, blockTop + blockSize * 0.7f),
        strokeWidth = grainWidth
    )

    // Top highlight edge
    drawLine(
        color = Color(0xFFE8D4B8),
        start = Offset(blockLeft, blockTop),
        end = Offset(blockLeft + blockSize, blockTop),
        strokeWidth = size * 0.06f
    )
    // Left highlight edge
    drawLine(
        color = Color(0xFFDEC9A8),
        start = Offset(blockLeft, blockTop),
        end = Offset(blockLeft, blockTop + blockSize),
        strokeWidth = size * 0.04f
    )

    // Bottom shadow edge
    drawLine(
        color = Color(0xFF8B6914),
        start = Offset(blockLeft, blockTop + blockSize),
        end = Offset(blockLeft + blockSize, blockTop + blockSize),
        strokeWidth = size * 0.04f
    )
    // Right shadow edge
    drawLine(
        color = Color(0xFF9A7520),
        start = Offset(blockLeft + blockSize, blockTop),
        end = Offset(blockLeft + blockSize, blockTop + blockSize),
        strokeWidth = size * 0.04f
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

// Player sprite data (8x8 pixels)
private val SPRITE_LEFT = arrayOf(
    intArrayOf(0, 0, 0, 1, 1, 1, 0, 0),
    intArrayOf(0, 1, 1, 1, 1, 1, 1, 0),
    intArrayOf(0, 0, 0, 1, 0, 0, 1, 0),
    intArrayOf(0, 0, 1, 0, 0, 0, 1, 0),
    intArrayOf(0, 0, 0, 1, 0, 1, 0, 0),
    intArrayOf(0, 1, 0, 1, 0, 1, 0, 0),
    intArrayOf(0, 0, 0, 0, 1, 0, 0, 0),
    intArrayOf(0, 0, 1, 1, 0, 1, 1, 0)
)

private val SPRITE_RIGHT = arrayOf(
    intArrayOf(0, 0, 1, 1, 1, 0, 0, 0),
    intArrayOf(0, 1, 1, 1, 1, 1, 1, 0),
    intArrayOf(0, 1, 0, 0, 1, 0, 0, 0),
    intArrayOf(0, 1, 0, 0, 0, 1, 0, 0),
    intArrayOf(0, 0, 1, 0, 1, 0, 0, 0),
    intArrayOf(0, 0, 1, 0, 1, 0, 1, 0),
    intArrayOf(0, 0, 0, 1, 0, 0, 0, 0),
    intArrayOf(0, 1, 1, 0, 1, 1, 0, 0)
)

private fun DrawScope.drawPlayer(x: Float, y: Float, size: Float, facing: Direction, holdingBlock: Boolean) {
    val pixelSize = size / 8f
    val sprite = if (facing == Direction.LEFT) SPRITE_LEFT else SPRITE_RIGHT

    // Held block (drawn first, behind player, above head)
    if (holdingBlock) {
        val blockSize = size * 0.85f
        val blockX = x + size / 2 - blockSize / 2
        val blockY = y - blockSize + size * 0.1f
        drawBlock(blockX, blockY, blockSize)
    }

    // Draw player sprite pixel by pixel
    for (row in 0 until 8) {
        for (col in 0 until 8) {
            if (sprite[row][col] == 1) {
                drawRect(
                    color = Color.White,
                    topLeft = Offset(x + col * pixelSize, y + row * pixelSize),
                    size = Size(pixelSize, pixelSize)
                )
            }
        }
    }
}

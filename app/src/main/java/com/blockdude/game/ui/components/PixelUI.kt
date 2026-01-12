package com.blockdude.game.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockdude.game.ui.theme.*

// Brick pattern background like arena walls
@Composable
fun BrickBackground(
    modifier: Modifier = Modifier,
    brickColor: Color = WallColor,
    mortarColor: Color = Color(0xFF252525),
    content: @Composable () -> Unit = {}
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawBrickPattern(brickColor, mortarColor)
        }
        content()
    }
}

private fun DrawScope.drawBrickPattern(
    brickColor: Color,
    mortarColor: Color
) {
    // Fill with brick color
    drawRect(color = brickColor, size = size)

    val brickHeight = 24.dp.toPx()
    val brickWidth = 48.dp.toPx()
    val mortarWidth = 2.dp.toPx()

    val rows = (size.height / brickHeight).toInt() + 1
    val cols = (size.width / brickWidth).toInt() + 2

    // Draw horizontal mortar lines
    for (row in 0..rows) {
        val y = row * brickHeight
        drawLine(
            color = mortarColor,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = mortarWidth
        )
    }

    // Draw vertical mortar lines (staggered)
    for (row in 0..rows) {
        val y = row * brickHeight
        val offset = if (row % 2 == 0) 0f else brickWidth / 2

        for (col in 0..cols) {
            val x = offset + col * brickWidth
            drawLine(
                color = mortarColor,
                start = Offset(x, y),
                end = Offset(x, y + brickHeight),
                strokeWidth = mortarWidth
            )
        }
    }
}

// Wood-styled button like game blocks
@Composable
fun WoodButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 200.dp,
    height: Dp = 56.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val blockColor = if (isPressed) BlockColor.copy(alpha = 0.8f) else BlockColor
            val margin = 2.dp.toPx()

            // Main block
            drawRect(
                color = blockColor,
                topLeft = Offset(margin, margin),
                size = Size(size.width - margin * 2, size.height - margin * 2)
            )

            // Wood grain lines
            val grainColor = Color(0xFFA07040)
            val grainWidth = 2.dp.toPx()

            drawLine(
                color = grainColor,
                start = Offset(size.width * 0.1f, size.height * 0.3f),
                end = Offset(size.width * 0.9f, size.height * 0.3f),
                strokeWidth = grainWidth
            )
            drawLine(
                color = grainColor,
                start = Offset(size.width * 0.15f, size.height * 0.55f),
                end = Offset(size.width * 0.85f, size.height * 0.55f),
                strokeWidth = grainWidth
            )
            drawLine(
                color = grainColor,
                start = Offset(size.width * 0.1f, size.height * 0.8f),
                end = Offset(size.width * 0.9f, size.height * 0.8f),
                strokeWidth = grainWidth
            )

            // Border
            drawRect(
                color = Color(0xFF805020),
                topLeft = Offset(margin, margin),
                size = Size(size.width - margin * 2, size.height - margin * 2),
                style = Stroke(width = 3.dp.toPx())
            )

            // Draw text
            val textStyle = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            val textLayoutResult = textMeasurer.measure(text, textStyle)
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    (size.width - textLayoutResult.size.width) / 2,
                    (size.height - textLayoutResult.size.height) / 2
                )
            )
        }
    }
}

// Small wood-styled button for icons (back, restart, etc.)
@Composable
fun WoodIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .size(size)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val blockColor = if (isPressed) BlockColor.copy(alpha = 0.8f) else BlockColor
            val margin = 2.dp.toPx()

            // Main block
            drawRect(
                color = blockColor,
                topLeft = Offset(margin, margin),
                size = Size(this.size.width - margin * 2, this.size.height - margin * 2)
            )

            // Border
            drawRect(
                color = Color(0xFF805020),
                topLeft = Offset(margin, margin),
                size = Size(this.size.width - margin * 2, this.size.height - margin * 2),
                style = Stroke(width = 2.dp.toPx())
            )
        }
        content()
    }
}

// Pixel-style frame/border
@Composable
fun PixelFrame(
    modifier: Modifier = Modifier,
    borderColor: Color = WallColor,
    borderWidth: Dp = 4.dp,
    backgroundColor: Color = GroundColor,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val border = borderWidth.toPx()

            // Background
            drawRect(
                color = backgroundColor,
                topLeft = Offset(border, border),
                size = Size(size.width - border * 2, size.height - border * 2)
            )

            // Outer border
            drawRect(
                color = borderColor,
                topLeft = Offset.Zero,
                size = size,
                style = Stroke(width = border)
            )

            // Inner highlight (top-left light, bottom-right dark)
            val highlightColor = borderColor.copy(alpha = 0.6f)
            val shadowColor = Color(0xFF1A1A1A)

            // Top highlight
            drawLine(
                color = highlightColor,
                start = Offset(border, border),
                end = Offset(size.width - border, border),
                strokeWidth = 2.dp.toPx()
            )
            // Left highlight
            drawLine(
                color = highlightColor,
                start = Offset(border, border),
                end = Offset(border, size.height - border),
                strokeWidth = 2.dp.toPx()
            )
            // Bottom shadow
            drawLine(
                color = shadowColor,
                start = Offset(border, size.height - border),
                end = Offset(size.width - border, size.height - border),
                strokeWidth = 2.dp.toPx()
            )
            // Right shadow
            drawLine(
                color = shadowColor,
                start = Offset(size.width - border, border),
                end = Offset(size.width - border, size.height - border),
                strokeWidth = 2.dp.toPx()
            )
        }
        Box(modifier = Modifier.padding(borderWidth)) {
            content()
        }
    }
}

// Authentic D-pad with pie/wedge-shaped buttons
@Composable
fun DPad(
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit,
    modifier: Modifier = Modifier,
    dpadSize: Dp = 200.dp
) {
    var pressedDirection by remember { mutableStateOf<DPadDirection?>(null) }

    Box(
        modifier = modifier.size(dpadSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    val inputSize = this.size
                    detectTapGestures(
                        onPress = { offset ->
                            val direction = getDPadDirection(offset, inputSize.width.toFloat(), inputSize.height.toFloat())
                            pressedDirection = direction
                            when (direction) {
                                DPadDirection.UP -> onUp()
                                DPadDirection.DOWN -> onDown()
                                DPadDirection.LEFT -> onLeft()
                                DPadDirection.RIGHT -> onRight()
                                null -> {}
                            }
                            tryAwaitRelease()
                            pressedDirection = null
                        }
                    )
                }
        ) {
            drawDPad(pressedDirection)
        }
    }
}

private enum class DPadDirection {
    UP, DOWN, LEFT, RIGHT
}

private fun getDPadDirection(offset: Offset, width: Float, height: Float): DPadDirection? {
    val centerX = width / 2
    val centerY = height / 2
    val dx = offset.x - centerX
    val dy = offset.y - centerY

    // Dead zone in center
    val deadZone = width * 0.15f
    if (dx * dx + dy * dy < deadZone * deadZone) {
        return null
    }

    // Determine direction based on angle
    return if (kotlin.math.abs(dx) > kotlin.math.abs(dy)) {
        if (dx > 0) DPadDirection.RIGHT else DPadDirection.LEFT
    } else {
        if (dy > 0) DPadDirection.DOWN else DPadDirection.UP
    }
}

private fun DrawScope.drawDPad(pressedDirection: DPadDirection?) {
    val w = size.width
    val h = size.height
    val centerX = w / 2
    val centerY = h / 2

    // D-pad dimensions
    val armWidth = w * 0.35f
    val armLength = w * 0.45f

    val baseColor = Color(0xFF3A3A3A)
    val buttonColor = Color(0xFF5A5A5A)
    val pressedColor = Color(0xFF7A7A7A)
    val borderColor = Color(0xFF2A2A2A)

    // Draw base (cross shape)
    // Vertical arm
    drawRect(
        color = baseColor,
        topLeft = Offset(centerX - armWidth / 2, centerY - armLength),
        size = Size(armWidth, armLength * 2)
    )
    // Horizontal arm
    drawRect(
        color = baseColor,
        topLeft = Offset(centerX - armLength, centerY - armWidth / 2),
        size = Size(armLength * 2, armWidth)
    )

    // Draw directional buttons (trapezoid wedges)
    // UP button
    val upColor = if (pressedDirection == DPadDirection.UP) pressedColor else buttonColor
    val upPath = Path().apply {
        moveTo(centerX - armWidth / 2 + 4, centerY - armWidth / 2 + 4)
        lineTo(centerX + armWidth / 2 - 4, centerY - armWidth / 2 + 4)
        lineTo(centerX + armWidth / 3, centerY - armLength + 8)
        lineTo(centerX - armWidth / 3, centerY - armLength + 8)
        close()
    }
    drawPath(upPath, upColor, style = Fill)
    drawPath(upPath, borderColor, style = Stroke(width = 2f))

    // DOWN button
    val downColor = if (pressedDirection == DPadDirection.DOWN) pressedColor else buttonColor
    val downPath = Path().apply {
        moveTo(centerX - armWidth / 2 + 4, centerY + armWidth / 2 - 4)
        lineTo(centerX + armWidth / 2 - 4, centerY + armWidth / 2 - 4)
        lineTo(centerX + armWidth / 3, centerY + armLength - 8)
        lineTo(centerX - armWidth / 3, centerY + armLength - 8)
        close()
    }
    drawPath(downPath, downColor, style = Fill)
    drawPath(downPath, borderColor, style = Stroke(width = 2f))

    // LEFT button
    val leftColor = if (pressedDirection == DPadDirection.LEFT) pressedColor else buttonColor
    val leftPath = Path().apply {
        moveTo(centerX - armWidth / 2 + 4, centerY - armWidth / 2 + 4)
        lineTo(centerX - armWidth / 2 + 4, centerY + armWidth / 2 - 4)
        lineTo(centerX - armLength + 8, centerY + armWidth / 3)
        lineTo(centerX - armLength + 8, centerY - armWidth / 3)
        close()
    }
    drawPath(leftPath, leftColor, style = Fill)
    drawPath(leftPath, borderColor, style = Stroke(width = 2f))

    // RIGHT button
    val rightColor = if (pressedDirection == DPadDirection.RIGHT) pressedColor else buttonColor
    val rightPath = Path().apply {
        moveTo(centerX + armWidth / 2 - 4, centerY - armWidth / 2 + 4)
        lineTo(centerX + armWidth / 2 - 4, centerY + armWidth / 2 - 4)
        lineTo(centerX + armLength - 8, centerY + armWidth / 3)
        lineTo(centerX + armLength - 8, centerY - armWidth / 3)
        close()
    }
    drawPath(rightPath, rightColor, style = Fill)
    drawPath(rightPath, borderColor, style = Stroke(width = 2f))

    // Center circle
    drawCircle(
        color = borderColor,
        radius = armWidth * 0.25f,
        center = Offset(centerX, centerY)
    )

    // Arrow indicators on each direction
    val arrowColor = Color.White.copy(alpha = 0.8f)
    val arrowSize = armWidth * 0.25f

    // Up arrow
    val upArrow = Path().apply {
        moveTo(centerX, centerY - armLength * 0.55f)
        lineTo(centerX - arrowSize / 2, centerY - armLength * 0.35f)
        lineTo(centerX + arrowSize / 2, centerY - armLength * 0.35f)
        close()
    }
    drawPath(upArrow, arrowColor, style = Fill)

    // Down arrow
    val downArrow = Path().apply {
        moveTo(centerX, centerY + armLength * 0.55f)
        lineTo(centerX - arrowSize / 2, centerY + armLength * 0.35f)
        lineTo(centerX + arrowSize / 2, centerY + armLength * 0.35f)
        close()
    }
    drawPath(downArrow, arrowColor, style = Fill)

    // Left arrow
    val leftArrow = Path().apply {
        moveTo(centerX - armLength * 0.55f, centerY)
        lineTo(centerX - armLength * 0.35f, centerY - arrowSize / 2)
        lineTo(centerX - armLength * 0.35f, centerY + arrowSize / 2)
        close()
    }
    drawPath(leftArrow, arrowColor, style = Fill)

    // Right arrow
    val rightArrow = Path().apply {
        moveTo(centerX + armLength * 0.55f, centerY)
        lineTo(centerX + armLength * 0.35f, centerY - arrowSize / 2)
        lineTo(centerX + armLength * 0.35f, centerY + arrowSize / 2)
        close()
    }
    drawPath(rightArrow, arrowColor, style = Fill)
}

// Action button (circular, distinct from D-pad)
@Composable
fun ActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .size(size)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val buttonColor = if (isPressed) AccentOrange.copy(alpha = 0.7f) else AccentOrange
            val borderColor = Color(0xFFB03040)

            // Outer ring
            drawCircle(
                color = borderColor,
                radius = this.size.width / 2 - 2.dp.toPx()
            )

            // Main button
            drawCircle(
                color = buttonColor,
                radius = this.size.width / 2 - 6.dp.toPx()
            )

            // Inner highlight
            drawCircle(
                color = Color.White.copy(alpha = 0.2f),
                radius = this.size.width / 2 - 12.dp.toPx(),
                center = Offset(this.size.width / 2 - 4.dp.toPx(), this.size.height / 2 - 4.dp.toPx())
            )

            // Action icon (grab/place)
            val iconColor = Color.White
            val iconPadding = this.size.width * 0.28f
            val barWidth = this.size.width * 0.08f

            // Top bar
            drawRect(
                color = iconColor,
                topLeft = Offset(iconPadding, iconPadding),
                size = Size(this.size.width - iconPadding * 2, barWidth)
            )
            // Left bar
            drawRect(
                color = iconColor,
                topLeft = Offset(iconPadding, iconPadding),
                size = Size(barWidth, this.size.height - iconPadding * 2)
            )
            // Right bar
            drawRect(
                color = iconColor,
                topLeft = Offset(this.size.width - iconPadding - barWidth, iconPadding),
                size = Size(barWidth, this.size.height - iconPadding * 2)
            )
            // Bottom bar
            drawRect(
                color = iconColor,
                topLeft = Offset(iconPadding, this.size.height - iconPadding - barWidth),
                size = Size(this.size.width - iconPadding * 2, barWidth)
            )
        }
    }
}

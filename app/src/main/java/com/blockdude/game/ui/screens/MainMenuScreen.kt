package com.blockdude.game.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockdude.game.ui.components.BrickBackground
import com.blockdude.game.ui.components.PixelFrame
import com.blockdude.game.ui.components.WoodButton
import com.blockdude.game.ui.theme.*

@Composable
fun MainMenuScreen(
    onPlayClick: () -> Unit,
    onLevelSelectClick: () -> Unit
) {
    BrickBackground(
        modifier = Modifier.fillMaxSize(),
        brickColor = WallColor,
        mortarColor = UIMortarColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title in pixel frame
            PixelFrame(
                borderColor = BlockColor,
                backgroundColor = GroundColor,
                borderWidth = 6.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "BLOCK",
                        color = PlayerColor,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 6.sp
                    )
                    Text(
                        text = "DUDE",
                        color = AccentOrange,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 6.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "A Classic Puzzle Game",
                color = TextWhite.copy(alpha = 0.7f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Buttons
            WoodButton(
                text = "PLAY",
                onClick = onPlayClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            WoodButton(
                text = "LEVELS",
                onClick = onLevelSelectClick
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Instructions in pixel frame
            PixelFrame(
                borderColor = WallColor,
                backgroundColor = GroundColor,
                borderWidth = 4.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "HOW TO PLAY",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "D-PAD  Move & climb",
                        color = TextWhite.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = "ACTION  Pick up / place block",
                        color = TextWhite.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Reach the door to win!",
                        color = DoorColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

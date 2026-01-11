package com.blockdude.game.data

import com.blockdude.game.game.Direction

data class Position(val x: Int, val y: Int)

data class GameState(
    val playerPosition: Position,
    val playerFacing: Direction = Direction.RIGHT,
    val holdingBlock: Boolean = false,
    val blocks: Set<Position>,
    val levelCompleted: Boolean = false,
    val moves: Int = 0
)

enum class CellType {
    EMPTY,
    WALL,
    BLOCK,
    DOOR
}

package ru.shamilkun.endless_tic_tac_toe.game

enum class Player(val symbol: String) {
    X("X"),
    O("O");

    fun next(): Player = if (this == X) O else X
}

data class GameState(
    val board: List<Player?> = List(BOARD_CELL_COUNT) { null },
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val winningCells: Set<Int> = emptySet(),
    val isDraw: Boolean = false
) {
    val isFinished: Boolean
        get() = winner != null || isDraw
}

const val BOARD_SIZE = 3
const val BOARD_CELL_COUNT = BOARD_SIZE * BOARD_SIZE

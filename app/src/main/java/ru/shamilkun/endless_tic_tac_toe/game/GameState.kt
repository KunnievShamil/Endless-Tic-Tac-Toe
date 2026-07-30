package ru.shamilkun.endless_tic_tac_toe.game

enum class Player(val symbol: String) {
    X("X"),
    O("O");

    fun next(): Player = if (this == X) O else X
}

data class GameState(
    val board: List<Player?> = List(BOARD_CELL_COUNT) { null },
    val moveHistory: Map<Player, List<Int>> = mapOf(
        Player.X to emptyList(),
        Player.O to emptyList()
    ),
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val winningCells: Set<Int> = emptySet()
) {
    val isFinished: Boolean
        get() = winner != null

    fun movesFor(player: Player): List<Int> {
        return moveHistory[player].orEmpty()
    }

    fun oldestCell(player: Player): Int? {
        val moves = movesFor(player)
        return moves.firstOrNull()
            .takeIf { moves.size == MAX_MARKS_PER_PLAYER }
    }
}

const val BOARD_SIZE = 3
const val BOARD_CELL_COUNT = BOARD_SIZE * BOARD_SIZE
const val MAX_MARKS_PER_PLAYER = 3

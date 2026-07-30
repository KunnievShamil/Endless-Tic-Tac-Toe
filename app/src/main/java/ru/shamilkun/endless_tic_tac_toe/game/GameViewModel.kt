package ru.shamilkun.endless_tic_tac_toe.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    var state by mutableStateOf(GameState())
        private set

    fun makeMove(cellIndex: Int) {
        if (cellIndex !in state.board.indices) return
        if (state.isFinished || state.board[cellIndex] != null) return

        val updatedBoard = state.board.toMutableList().apply {
            this[cellIndex] = state.currentPlayer
        }
        val winningCells = findWinningCells(updatedBoard, state.currentPlayer)
        val hasWinner = winningCells.isNotEmpty()
        val isDraw = !hasWinner && updatedBoard.none { it == null }

        state = state.copy(
            board = updatedBoard,
            currentPlayer = if (hasWinner || isDraw) {
                state.currentPlayer
            } else {
                state.currentPlayer.next()
            },
            winner = state.currentPlayer.takeIf { hasWinner },
            winningCells = winningCells,
            isDraw = isDraw
        )
    }

    fun restart() {
        state = GameState()
    }

    private fun findWinningCells(
        board: List<Player?>,
        player: Player
    ): Set<Int> {
        return WINNING_LINES
            .firstOrNull { line -> line.all { board[it] == player } }
            ?.toSet()
            .orEmpty()
    }

    private companion object {
        val WINNING_LINES = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )
    }
}

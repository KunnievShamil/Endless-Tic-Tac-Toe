package ru.shamilkun.endless_tic_tac_toe.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(
    state: GameState,
    onCellClick: (Int) -> Unit,
    onRestartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = statusText(state),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Column(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
                .sizeIn(maxWidth = 420.dp)
        ) {
            repeat(BOARD_SIZE) { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(BOARD_SIZE) { column ->
                        val cellIndex = row * BOARD_SIZE + column

                        GameCell(
                            player = state.board[cellIndex],
                            isOldest = state.board[cellIndex]?.let { player ->
                                state.oldestCell(player) == cellIndex
                            } == true,
                            isWinning = cellIndex in state.winningCells,
                            enabled = !state.isFinished && state.board[cellIndex] == null,
                            onClick = { onCellClick(cellIndex) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        if (state.isFinished) {
            Button(
                onClick = onRestartClick,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text(text = "Играть снова")
            }
        }
    }
}

@Composable
private fun GameCell(
    player: Player?,
    isOldest: Boolean,
    isWinning: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isWinning) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val markerColor = when (player) {
        Player.X -> MaterialTheme.colorScheme.primary
        Player.O -> Color(0xFFD84315)
        null -> Color.Transparent
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
            .background(backgroundColor)
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player?.symbol.orEmpty(),
            color = markerColor.copy(
                alpha = if (isOldest && !isWinning) 0.35f else 1f
            ),
            fontSize = 54.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun statusText(state: GameState): String {
    return when {
        state.winner != null -> "Победил ${state.winner.symbol}"
        else -> "Ход игрока ${state.currentPlayer.symbol}"
    }
}

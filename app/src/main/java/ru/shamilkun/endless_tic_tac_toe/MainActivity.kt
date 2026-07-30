package ru.shamilkun.endless_tic_tac_toe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.shamilkun.endless_tic_tac_toe.game.GameScreen
import ru.shamilkun.endless_tic_tac_toe.game.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val gameViewModel: GameViewModel = viewModel()

                GameScreen(
                    state = gameViewModel.state,
                    onCellClick = gameViewModel::makeMove,
                    onRestartClick = gameViewModel::restart
                )
            }
        }
    }
}

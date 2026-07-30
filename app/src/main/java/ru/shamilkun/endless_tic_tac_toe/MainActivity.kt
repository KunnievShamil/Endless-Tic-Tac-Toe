package ru.shamilkun.endless_tic_tac_toe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModelProvider
import ru.shamilkun.endless_tic_tac_toe.game.GameScreen
import ru.shamilkun.endless_tic_tac_toe.game.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        setContent {
            MaterialTheme {
                GameScreen(
                    state = gameViewModel.state,
                    onCellClick = gameViewModel::makeMove,
                    onRestartClick = gameViewModel::restart
                )
            }
        }
    }
}

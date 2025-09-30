package net.hubbu.kotlin_wordle.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import net.hubbu.kotlin_wordle.R
import net.hubbu.kotlin_wordle.data.GameUiState
import net.hubbu.kotlin_wordle.data.LetterModel
import net.hubbu.kotlin_wordle.ui.GameViewModel
import net.hubbu.kotlin_wordle.ui.theme.KotlinWordleTheme

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    val viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GameScreen(
        uiState = uiState,
        targetWordMatches = viewModel.targetWordMatches,
        maxWordLength = viewModel.maxWordLength,
        maxWordCount = viewModel.maxWordCount,
        keyMatches = viewModel.getKeyMatchStatus(),
        endGameMessage = viewModel::getEndGameMessage,
        onKeyPress = viewModel::onKeyPress,
        onEnter = viewModel::onEnter,
        onDelete = viewModel::onDelete,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    uiState: GameUiState,
    targetWordMatches: Map<Char, List<Int>>,
    maxWordLength: Int,
    maxWordCount: Int,
    keyMatches: Map<Char, LetterModel>,
    modifier: Modifier = Modifier,
    endGameMessage: () -> String = {""},
    onKeyPress: (String) -> Unit = {},
    onDelete: () -> Unit = {},
    onEnter: () -> Unit = {},
) {
    Scaffold(
        modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { /* TODO: do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Instructions"
                        )
                    }
                    IconButton(onClick = { /* TODO: do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            AnimatedVisibility(
                visible = uiState.isGameOver,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter) // Position it at the top center
                    .padding(top = 8.dp) // Adjust padding to move it down
            ) {
                EndGameMessage(endGameMessage())
            }
            
            Column {
                Board(
                    guessedWords = uiState.guessedWords,
                    currentWord = uiState.currentWord,
                    targetWordMatches = targetWordMatches,
                    maxWordLength = maxWordLength,
                    maxWordCount = maxWordCount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2.5f),
                )
                Keyboard(
                    keyMatches = keyMatches,
                    onKeyPress = onKeyPress,
                    onEnter = onEnter,
                    onDelete = onDelete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}
@Composable
fun EndGameMessage(message: String, modifier: Modifier = Modifier) {
    return Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.DarkGray)
            .width(86.dp)
            .height(56.dp)
    ) {
        Text(message, color = Color.White)
    }
}

@Preview(showBackground = false)
@Composable
fun EndGameMessagePreview() {
    KotlinWordleTheme {
        EndGameMessage("SPLENDID")
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    KotlinWordleTheme {
        GameScreen(
            uiState = GameUiState(
                guessedWords = listOf(
                    "ABOUT",
                    "TABLE",
                    "CHAIR",
                    "PLANT",
                ),
                currentWord = "AUD",
                isGameOver = false,
                didWin = false,
            ),
            targetWordMatches = mapOf(
                'A' to listOf(0),
                'U' to listOf(1),
                'D' to listOf(2),
                'I' to listOf(3),
                'O' to listOf(4),
            ),
            maxWordLength = 5,
            maxWordCount = 6,
            keyMatches = mapOf(
                'A' to LetterModel.Absent('A'),
                'U' to LetterModel.Present('U'),
                'D' to LetterModel.Correct('D'),
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}
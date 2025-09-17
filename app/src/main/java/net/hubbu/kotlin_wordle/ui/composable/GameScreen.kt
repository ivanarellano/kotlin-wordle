package net.hubbu.kotlin_wordle.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import net.hubbu.kotlin_wordle.data.LetterModel
import net.hubbu.kotlin_wordle.ui.GameViewModel
import net.hubbu.kotlin_wordle.ui.theme.KotlinWordleTheme

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    val viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GameScreen(
        guessedWords = uiState.guessedWords,
        currentWord = uiState.currentWord,
        targetWordMatches = viewModel.targetWordMatches,
        maxWordLength = viewModel.maxWordLength,
        maxWordCount = viewModel.maxWordCount,
        keyMatches = viewModel.getKeyMatchStatus(),
        onKeyPress = viewModel::onKeyPress,
        onEnter = viewModel::onEnter,
        onDelete = viewModel::onDelete,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    guessedWords: List<String>,
    currentWord: String,
    targetWordMatches: Map<Char, List<Int>>,
    maxWordLength: Int,
    maxWordCount: Int,
    keyMatches: Map<Char, LetterModel>,
    modifier: Modifier = Modifier,
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
                    Text("Wordle")
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
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Board(
                guessedWords = guessedWords,
                currentWord = currentWord,
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

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    KotlinWordleTheme {
        GameScreen(
            guessedWords = listOf(
                "ABOUT",
                "TABLE",
                "CHAIR",
                "PLANT",
            ),
            currentWord = "AUD",
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
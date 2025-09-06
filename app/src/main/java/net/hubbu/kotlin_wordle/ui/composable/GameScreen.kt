package net.hubbu.kotlin_wordle.ui.composable

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.map
import net.hubbu.kotlin_wordle.ui.GameViewModel
import net.hubbu.kotlin_wordle.ui.theme.KotlinWordleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                guessedWords = uiState.guessedWords,
                currentWord = uiState.currentWord,
                targetWordMatches = viewModel.targetWordMatches,
                maxWordLength = viewModel.maxWordLength,
                maxWordCount = viewModel.maxWordCount,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2.5f),
            )
            Keyboard(
                keyMatches = viewModel.getKeyMatchStatus(),
                onKeyPress = viewModel::onKeyPress,
                onDelete = viewModel::onDelete,
                onEnter = viewModel::onEnter,
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
        GameScreen(modifier = Modifier.fillMaxSize())
    }
}
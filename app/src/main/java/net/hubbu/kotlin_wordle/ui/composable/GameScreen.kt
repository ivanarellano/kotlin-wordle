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
import net.hubbu.kotlin_wordle.ui.GameScreenViewModel
import net.hubbu.kotlin_wordle.ui.theme.KotlinWordleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    keyboardViewModel: GameScreenViewModel = viewModel()
) {
    // Collect the current word from the ViewModel
    // Make sure your KeyboardViewModel.currentWord is a StateFlow
    val currentWord by keyboardViewModel.currentWord.collectAsStateWithLifecycle()

    // TODO: Move target and guessed words to ViewModel
    val targetWord = "AUDIO"
    val guessedWords = listOf(
        "ABOUT",
        "TABLE",
        "CHAIR",
        "PLANT",
    )

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
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Instructions"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
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
                target = targetWord,
                guessedWords = guessedWords,
                currentWord = currentWord,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2.5f),
            )
            Keyboard(
                targetMap = getIndexedLetterMap(targetWord),
                guessedWords = guessedWords,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

// Example: word = "APPLE", output = {A=[0], P=[1, 4], L=[2], E=[3]}
fun getIndexedLetterMap(word: String): Map<Char, List<Int>> {
    val targetMap = mutableMapOf<Char, MutableList<Int>>().withDefault { mutableListOf() }

    for (i in word.indices) {
        val letter = word[i]
        val list = targetMap.getValue(letter)

        list.add(i)
        targetMap[letter] = list
    }
    return targetMap.toMap()
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    KotlinWordleTheme {
        GameScreen(modifier = Modifier.fillMaxSize())
    }
}
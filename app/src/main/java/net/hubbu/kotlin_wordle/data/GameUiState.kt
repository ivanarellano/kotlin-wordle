package net.hubbu.kotlin_wordle.data

data class GameUiState(
    val currentWord: String = "",
//    val guessedWords: List<String> = emptyList(),
    // TODO: Use real guessed words
    val guessedWords: List<String> = listOf(
        "ABOUT",
        "TABLE",
        "CHAIR",
        "PLANT",
    ),
    val isGameOver: Boolean = false,
)
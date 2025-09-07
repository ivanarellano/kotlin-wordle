package net.hubbu.kotlin_wordle.data

data class GameUiState(
    val currentWord: String = "",
    // TODO: Use real target word
    val targetWord: String = "AUDIO",
//    val guessedWords: List<String> = emptyList(),
    // TODO: Use real guessed words
    val guessedWords: List<String> = listOf(
        "ABOUT",
        "TABLE",
        "CHAIR",
        "PLANT",
    ),
    val isGameOver: Boolean = false,
    val didWin: Boolean = false,
)
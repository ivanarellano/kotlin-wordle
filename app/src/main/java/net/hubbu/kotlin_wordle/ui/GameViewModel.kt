package net.hubbu.kotlin_wordle.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.hubbu.kotlin_wordle.GameApplication
import net.hubbu.kotlin_wordle.data.GameUiState
import net.hubbu.kotlin_wordle.data.LetterModel
import net.hubbu.kotlin_wordle.data.WordListRepository

class GameViewModel(private val wordListRepo: WordListRepository) : ViewModel() {
    val maxWordLength: Int = 5
    val maxWordCount: Int = 6

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    val targetWordMatches: Map<Char, List<Int>> = getIndexedLetterMap(_uiState.value.targetWord)

    // Used to check for valid guesses
    private val wordleGuessList: List<String> by lazy {
        wordListRepo.loadList("nonwordles.json")
    }
    private val wordleSolutionList: List<String> by lazy {
        wordListRepo.loadList("wordles.json")
    }

    // When a key is pressed
    fun onKeyPress(keyText: String) {
        if (_uiState.value.currentWord.length < maxWordLength) {
            _uiState.update { currentState ->
                currentState.copy(currentWord = currentState.currentWord + keyText)
            }
        }
    }

    // When the delete key is pressed
    fun onDelete() {
        if (_uiState.value.currentWord.isNotEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(currentWord = currentState.currentWord.dropLast(1))
            }
        }
    }

    // When the enter key is pressed
    fun onEnter() {
        if (_uiState.value.isGameOver) return

        if (_uiState.value.currentWord.length < maxWordLength) {
            Log.d("GameViewModel", "Word too short")
            return
        }

        val didWin = _uiState.value.currentWord == _uiState.value.targetWord
        val didLose = _uiState.value.guessedWords.size >= maxWordCount - 1

        if (didWin || didLose) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGameOver = true,
                    didWin = didWin
                )
            }
        }

        // TODO: Benchmark against list.binarySearch(word)
        // Check if the current word is not in both wordle guess or solution list
        if (_uiState.value.currentWord.lowercase() !in wordleGuessList
            && _uiState.value.currentWord.lowercase() !in wordleSolutionList) {
            Log.d("GameViewModel", "Word not valid")
            return
        }

        _uiState.update { currentState ->
            currentState.copy(
                currentWord = "",
                guessedWords = currentState.guessedWords + currentState.currentWord
            )
        }
    }

    // Given a target word and guessed words, return a map of LetterModel for each matched letter
    fun getKeyMatchStatus(): Map<Char, LetterModel> {
        val keyMatches = mutableMapOf<Char, LetterModel>()

        for (word in _uiState.value.guessedWords) {
            for (i in word.indices) {
                val letter = word[i]

                keyMatches[word[i]] = if (!targetWordMatches.containsKey(letter)) {
                    LetterModel.Absent(char = letter)
                }
                else if (targetWordMatches.getValue(letter).contains(i)) {
                    LetterModel.Correct(char = letter)
                }
                else if (targetWordMatches.containsKey(letter) && !targetWordMatches.getValue(letter).contains(i)) {
                    LetterModel.Present(char = letter)
                }
                else {
                    LetterModel.DefaultKey(char = letter)
                }
            }
        }
        return keyMatches
    }

    // Example: word = "APPLE", output = {A=[0], P=[1, 4], L=[2], E=[3]}
    private fun getIndexedLetterMap(word: String): Map<Char, List<Int>> {
        val targetMap = mutableMapOf<Char, MutableList<Int>>().withDefault { mutableListOf() }

        for (i in word.indices) {
            val letter = word[i]
            val list = targetMap.getValue(letter)

            list.add(i)
            targetMap[letter] = list
        }
        return targetMap.toMap()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GameApplication)
                val wordListRepo = application.container.wordListRepo
                GameViewModel(wordListRepo)
            }
        }
    }
}

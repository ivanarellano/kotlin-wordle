package net.hubbu.kotlin_wordle.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class KeyboardViewModel : ViewModel() {
    // TODO: Extract into a class
    val wordLength = 5
    val currentWord: MutableStateFlow<String> = MutableStateFlow("")

    // When a text key is pressed
    fun onKeyPress(keyText: String) {
        Log.d("KeyboardViewModel", "Key pressed: $keyText")
        if (currentWord.value.length < wordLength) {
            currentWord.update { currentWord.value + keyText }
        }
    }

    // When the delete key is pressed
    fun onDelete() {
        Log.d("KeyboardViewModel", "Delete pressed")
        if (currentWord.value.isNotEmpty()) {
            currentWord.update { currentWord.value.dropLast(1) }
        }
    }

    // When the enter key is pressed
    fun onEnter() {
        Log.d("KeyboardViewModel", "Enter pressed")
        // TODO: Validate the word (currently clears)
        // guessedWords.update { it + _currentWord.value }
        currentWord.value = ""
    }
}

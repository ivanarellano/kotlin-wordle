package net.hubbu.kotlin_wordle.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import net.hubbu.kotlin_wordle.data.LetterModel

class GameScreenViewModel : ViewModel() {
    val wordLength: Int
        get() = 5

    val maxWordCount: Int
        get() = 6

    // TODO: Use a real word list
    val targetWordMap: Map<Char, List<Int>> by lazy {
        getIndexedLetterMap("AUDIO")
    }

    // TODO: Use real guessed words
    val guessedWords = listOf(
        "ABOUT",
        "TABLE",
        "CHAIR",
        "PLANT",
    )

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

    // Given a target word and guessed words, return a map of LetterModel for each matched letter
    fun getKeyMatchStatus(): Map<Char, LetterModel> {
        val keyMatches = mutableMapOf<Char, LetterModel>()

        for (word in guessedWords) {
            for (i in word.indices) {
                val letter = word[i]

                keyMatches[word[i]] = if (!targetWordMap.containsKey(letter)) {
                    LetterModel.Absent(char = letter)
                }
                else if (targetWordMap.getValue(letter).contains(i)) {
                    LetterModel.Correct(char = letter)
                }
                else if (targetWordMap.containsKey(letter) && !targetWordMap.getValue(letter).contains(i)) {
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
}

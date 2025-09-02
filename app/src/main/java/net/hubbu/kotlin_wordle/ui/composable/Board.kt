package net.hubbu.kotlin_wordle.ui.composable

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextGranularity.Companion.Word
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.hubbu.kotlin_wordle.ui.LetterModel
import net.hubbu.kotlin_wordle.ui.theme.getColor

// TODO
const val maxWordCount = 6
const val WORD_LENGTH = 5

enum class LetterType {
    Empty, Correct, Absent, Present
}

@Composable
fun Board(
    modifier: Modifier = Modifier,
    target: String,
    guessedWords: List<String>,
    currentWord: String
) {
    val displayedRows = mutableListOf<String>()
    displayedRows.addAll(guessedWords)

    // Add currentWord if there's space for it (i.e., we haven't guessed maxWordCount words yet)
    if (guessedWords.size < maxWordCount) {
        displayedRows.add(currentWord)
    }

    // Add any remaining empty rows to fill up to maxWordCount
    val emptyRowCount = maxWordCount - displayedRows.size
    displayedRows.addAll(List(emptyRowCount) { " ".repeat(WORD_LENGTH) })

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

    val targetMap = getIndexedLetterMap(word = target)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier.wrapContentSize(),
    ) {
        for (i in displayedRows.indices) {
            val isCurrentWord = i == guessedWords.size
            Word(displayedRows[i], targetMap, isCurrentWord)
        }
    }
}

@Composable
fun Word(
    word: String,
    targetMap: Map<Char, List<Int>>,
    isCurrentWord: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        // Ensure the word displayed has the correct length, padding with spaces if necessary
        val displayableWord = word.padEnd(WORD_LENGTH, ' ')

        for (i in displayableWord.indices) {
            val letter = displayableWord[i]

            if (letter.isWhitespace()) {
                Letter(LetterModel.Empty())
            }
            else if (isCurrentWord || !targetMap.containsKey(letter)) {
                Letter(LetterModel.Absent(char = letter))
            }
            else if (targetMap.getValue(letter).contains(i)) {
                Letter(LetterModel.Correct(char = letter))
            }
            else {
                Letter(LetterModel.Present(char = letter))
            }
        }
    }
}

@Composable
fun Letter(model: LetterModel, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(62.dp)
            .height(62.dp)
            .border(width = 2.dp, color = model.borderColor.getColor())
            .background(color = model.bgColor.getColor())
            .wrapContentSize(),
    ) {
        if (model.type != LetterType.Empty)
            Text(
                text = "${model.char}",
                style = MaterialTheme.typography.headlineLarge,
            )
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    Board(
        target = "AUDIO",
        guessedWords = listOf(
            "ABOUT",
            "TABLE",
            "CHAIR",
            "PLANT",
        ),
        currentWord = "AUD  ")
}
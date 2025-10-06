package net.hubbu.kotlin_wordle.ui.composable

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.hubbu.kotlin_wordle.data.LetterModel
import net.hubbu.kotlin_wordle.ui.theme.getColor

enum class LetterType {
    Empty, Correct, Absent, Present, Guess
}

@Composable
fun Board(
    guessedWords: List<String>,
    currentWord: String,
    targetWordMatches: Map<Char, List<Int>>,
    maxWordLength: Int,
    maxWordCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier.wrapContentSize(),
    ) {
        val displayedWords = mutableListOf<String>()
        displayedWords.addAll(guessedWords)

        // Add currentWord if there's space for it (i.e., we haven't guessed maxWordCount words yet)
        if (guessedWords.size < maxWordCount) {
            // Ensure the word displayed has the correct length, padding with spaces if necessary
            displayedWords.add(currentWord.padEnd(maxWordLength, ' '))
        }

        // Add any remaining empty rows to fill up to MAX_WORD_COUNT
        val emptyRowCount = maxWordCount - displayedWords.size
        displayedWords.addAll(
            List(emptyRowCount) { " ".repeat(maxWordLength) }
        )

        for (i in displayedWords.indices) {
            val isCurrentWord = i == guessedWords.size
            Word(
                displayedWords[i],
                targetWordMatches,
                isCurrentWord = isCurrentWord,
            )
        }
    }
}

@Composable
fun Word(
    word: String,
    targetWordMap: Map<Char, List<Int>>,
    modifier: Modifier = Modifier,
    isCurrentWord: Boolean = false,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        for (i in word.indices) {
            val letter = word[i]

            if (letter.isWhitespace()) {
                Letter(LetterModel.Empty())
            }
            else if (isCurrentWord) {
                Letter(LetterModel.Guess(char = letter))
            }
            else if (!targetWordMap.containsKey(letter)) {
                Letter(LetterModel.Absent(char = letter))
            }
            else if (targetWordMap.getValue(letter).contains(i)) {
                Letter(LetterModel.Correct(char = letter))
            }
            else {
                Letter(LetterModel.Present(char = letter))
            }
        }
    }
}

@Composable
fun Letter(model: LetterModel, modifier: Modifier = Modifier, size: Dp = 62.dp) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(size)
            .height(size)
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
        targetWordMatches = mapOf(
            'A' to listOf(0),
            'U' to listOf(1),
            'D' to listOf(2),
            'I' to listOf(3),
            'O' to listOf(4),
        ),
        guessedWords = listOf(
            "ABOUT",
            "TABLE",
            "CHAIR",
            "PLANT",
        ),
        currentWord = "AUD",
        maxWordLength = 5,
        maxWordCount = 6,
    )
}
package net.hubbu.kotlin_worlde.ui.composable

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
import androidx.compose.ui.unit.dp

// TODO
const val target = "PRANK"
const val maxWordCount = 6

@Composable
fun Letter(character: Char?, modifier: Modifier = Modifier) {
//    Mutable attributes
//    - character, border color, background color
//    States
//    - empty, guessing, correct, incorrect, partial
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(62.dp)
            .height(62.dp)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.primaryContainer)
            .wrapContentSize(),
    ) {
        if (character != ' ')
            Text(
                text = "$character",
                style = MaterialTheme.typography.headlineLarge,
            )
    }
}

@Composable
fun Word(word: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        for (letter in word) {
            Letter(letter)
        }
    }
}

@Composable
fun Board(guessedWords: List<String>, modifier: Modifier = Modifier) {
    val emptyRows = maxWordCount - guessedWords.size
    val wordRows = guessedWords + List(emptyRows) { "     " }
//    State
//    - Words
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier.wrapContentSize(),
    ) {
        for (word in wordRows) {
            Word(word)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    Board(
        guessedWords = listOf(
            "ABOUT",
            "TABLE",
            "CHAIR",
            "PLANT",
        ))
}
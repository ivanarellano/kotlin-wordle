package net.hubbu.kotlin_wordle.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.hubbu.kotlin_wordle.data.LetterModel
import net.hubbu.kotlin_wordle.ui.theme.KotlinWordleTheme

@Composable
fun HowToPlaySheet() {
    @Composable
    fun BoldExampleText(letter: String, instruction: String) {
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(letter)
            }
            append(instruction)
        }
        Text(text = annotatedString)
    }

    Column(modifier = Modifier.padding(horizontal = 32.dp)) {
        Text(
            text = "How to play",
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = "Guess the word in 6 tries",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(12.dp))

        Text("Each guess must be a valid 5 letter word.")
        Text("The color of the tiles will change to show how close your guess was to the word.")
        Spacer(Modifier.height(12.dp))

        Text(
            text = "Examples",
            fontWeight = FontWeight.Bold,
        )

        val letterSize = 42.dp

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Letter(LetterModel.Correct(char = 'W'), size = letterSize)
            Letter(LetterModel.Guess(char = 'O'), size = letterSize)
            Letter(LetterModel.Guess(char = 'R'), size = letterSize)
            Letter(LetterModel.Guess(char = 'D'), size = letterSize)
            Letter(LetterModel.Guess(char = 'Y'), size = letterSize)

        }
        BoldExampleText("W", " is in the word and in the correct spot.")
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Letter(LetterModel.Guess(char = 'L'), size = letterSize)
            Letter(LetterModel.Present(char = 'I'), size = letterSize)
            Letter(LetterModel.Guess(char = 'G'), size = letterSize)
            Letter(LetterModel.Guess(char = 'H'), size = letterSize)
            Letter(LetterModel.Guess(char = 'T'), size = letterSize)

        }
        BoldExampleText("I", " is in the word but in the wrong spot.")
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Letter(LetterModel.Guess(char = 'R'), size = letterSize)
            Letter(LetterModel.Guess(char = 'O'), size = letterSize)
            Letter(LetterModel.Guess(char = 'G'), size = letterSize)
            Letter(LetterModel.Absent(char = 'U'), size = letterSize)
            Letter(LetterModel.Guess(char = 'E'), size = letterSize)

        }
        BoldExampleText("U", " is not in the word in any spot.")
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun HowToPlaySheetPreview() {
    KotlinWordleTheme {
        HowToPlaySheet()
    }
}
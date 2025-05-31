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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// TODO
const val maxWordCount = 6

enum class LetterType {
    Empty, Correct, Incorrect, Partial
}

enum class ColorType {
    Transparent, MaterialPrimary, Green, Yellow
}

@Composable
fun ColorType.getColor() : Color = when (this) {
    ColorType.Green -> Color(0xFF6CA965)
    ColorType.Yellow -> Color(0xFFC8B653)
    ColorType.Transparent -> Color.Transparent
    ColorType.MaterialPrimary -> MaterialTheme.colorScheme.primaryContainer
}

sealed class LetterModel(
    open val char: Char,
    open val bgColor: ColorType,
    open val borderColor: ColorType,
) {
    abstract val type: LetterType

    class Empty(
        override val char: Char = ' ',
        override val bgColor: ColorType = ColorType.Transparent,
        override val borderColor: ColorType = ColorType.MaterialPrimary
    ) : LetterModel(char, bgColor, borderColor) {
        override val type: LetterType = LetterType.Empty
    }

    class Correct(
        override val char: Char,
        override val bgColor: ColorType = ColorType.Green,
        override val borderColor: ColorType = ColorType.Transparent
    ) : LetterModel(char, bgColor, borderColor) {
        override val type: LetterType = LetterType.Correct
    }

    class Incorrect(
        override val char: Char,
        override val bgColor: ColorType = ColorType.Yellow,
        override val borderColor: ColorType = ColorType.Transparent
    ) : LetterModel(char, bgColor, borderColor) {
        override val type: LetterType = LetterType.Incorrect
    }

    class Partial(
        override val char: Char,
        override val bgColor: ColorType = ColorType.MaterialPrimary,
        override val borderColor: ColorType = ColorType.Transparent
    ) : LetterModel(char, bgColor, borderColor) {
        override val type: LetterType = LetterType.Partial
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

@Composable
fun Word(word: String, targetMap: Map<Char, List<Int>>, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        for (i in word.indices) {
            val letter = word[i]

            if (letter.isWhitespace()) {
                Letter(LetterModel.Empty())
                continue
            }

            if (!targetMap.containsKey(letter)) {
                Letter(LetterModel.Incorrect(char = letter))
            }
            else if (targetMap.getValue(letter).contains(i)) {
                Letter(LetterModel.Correct(char = letter))
            }
            else {
                Letter(LetterModel.Partial(char = letter))
            }
        }
    }
}

@Composable
fun Board(target: String, guessedWords: List<String>, modifier: Modifier = Modifier) {
    val emptyRows = maxWordCount - guessedWords.size
    val wordRows = guessedWords + List(emptyRows) { "     " }

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
        for (word in wordRows) {
            Word(word, targetMap)
        }
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
        ))
}
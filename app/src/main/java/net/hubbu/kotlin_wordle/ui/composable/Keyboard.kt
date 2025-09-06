package net.hubbu.kotlin_wordle.ui.composable

import android.util.Log.i
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.hubbu.kotlin_wordle.R
import net.hubbu.kotlin_wordle.ui.KeyboardViewModel
import net.hubbu.kotlin_wordle.data.LetterModel
import net.hubbu.kotlin_wordle.ui.theme.getColor

enum class KeyboardIcon(val res: Int, val description: String) {
    Delete(R.drawable.outline_backspace_24, "Backspace")
}

@Composable
fun Keyboard(
    targetMap: Map<Char, List<Int>>,
    modifier: Modifier = Modifier,
    guessedWords: List<String> = emptyList(),
    viewModel: KeyboardViewModel = viewModel()
) {
    val keyMatches = viewModel.getKeyMatchStatus(targetMap, guessedWords)

    @Composable
    fun KeyRow(keys: String, isBottomRow: Boolean = false) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            if (isBottomRow) {
                KeyButton(text = "ENTER", onClick = { viewModel.onEnter() })
            }

            for (letter in keys) {
                val model = keyMatches[letter] ?: LetterModel.DefaultKey(char = letter)
                KeyButton(
                    model = model,
                    text = "$letter",
                    onClick = { viewModel.onKeyPress("$letter" )}
                )
            }

            if (isBottomRow) {
                KeyButton(icon = KeyboardIcon.Delete, onClick = { viewModel.onDelete() })
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        modifier = modifier.wrapContentSize(),
    ) {
        KeyRow("QWERTYUIOP")
        KeyRow("ASDFGHJKL")
        KeyRow(keys = "ZXCVBNM", isBottomRow = true)
    }
}

@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    model: LetterModel = LetterModel.DefaultKey(),
    text: String? = null,
    icon: KeyboardIcon? = null,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(onClick = onClick)
            .defaultMinSize(minWidth = 30.dp, minHeight = 56.dp)
            .widthIn(min = 0.dp, max = 48.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = model.bgColor.getColor())
            .padding(horizontal = 4.dp)
            .wrapContentSize(),
    ) {
        val style = MaterialTheme.typography.titleMedium
        var fontSize by remember {
            mutableStateOf(style.fontSize)
        }

        if (text != null) {
            Text(
                text = text,
                style = style,
                maxLines = 1,
                fontSize = fontSize,
                onTextLayout = {
                    if (it.multiParagraph.didExceedMaxLines) {
                        fontSize *= .8F
                    }
                }
            )
        }
        if (icon != null) {
            Image(
                painter = painterResource(id = icon.res),
                contentDescription = icon.description,
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    Keyboard(
        // TODO: Replace targetMap with targetWord
//        targetWord = "AUDIO",
        targetMap = mapOf(
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
    )
}

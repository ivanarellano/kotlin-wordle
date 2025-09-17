package net.hubbu.kotlin_wordle.ui.composable

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
import net.hubbu.kotlin_wordle.R
import net.hubbu.kotlin_wordle.data.LetterModel
import net.hubbu.kotlin_wordle.ui.theme.getColor

enum class KeyboardIcon(val res: Int, val description: String) {
    Delete(R.drawable.outline_backspace_24, "Backspace")
}

@Composable
fun Keyboard(
    keyMatches: Map<Char, LetterModel>,
    modifier: Modifier = Modifier,
    onKeyPress: (String) -> Unit = {},
    onEnter: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    @Composable
    fun KeyRow(keys: String, isBottomRow: Boolean = false) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            if (isBottomRow) {
                KeyButton(text = "ENTER", onClick = { onEnter() })
            }

            for (letter in keys) {
                val model = keyMatches[letter] ?: LetterModel.DefaultKey(char = letter)
                KeyButton(
                    model = model,
                    text = "$letter",
                    onClick = { onKeyPress("$letter" )}
                )
            }

            if (isBottomRow) {
                KeyButton(icon = KeyboardIcon.Delete, onClick = { onDelete() })
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
        keyMatches = mapOf(
            'A' to LetterModel.Absent('A'),
            'U' to LetterModel.Present('U'),
            'D' to LetterModel.Correct('D'),
        )
    )
}

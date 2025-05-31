package net.hubbu.kotlin_worlde.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.hubbu.kotlin_worlde.R

enum class KeyboardIcon(val res: Int, val description: String) {
    Delete(R.drawable.outline_backspace_24, "Backspace")
}

@Composable
fun KeyboardButton(modifier: Modifier = Modifier, text: String? = null, icon: KeyboardIcon? = null) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .defaultMinSize(minWidth = 28.dp, minHeight = 56.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 8.dp)
            .wrapContentSize(),
    ) {
        if (text != null) {
            Text(
                text = "$text",
                style = MaterialTheme.typography.titleMedium,
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

@Composable
fun Keyboard(modifier: Modifier = Modifier) {
    @Composable
    fun KeyRow(keys: String, isBottomRow: Boolean = false) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            if (isBottomRow) {
                KeyboardButton(text = "ENTER")
            }
            for (char in keys) {
                KeyboardButton(text = "$char")
            }
            if (isBottomRow) {
                KeyboardButton(icon = KeyboardIcon.Delete)
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
        KeyRow(keys ="ZXCVBNM", isBottomRow = true)
    }
}

@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    Keyboard()
}
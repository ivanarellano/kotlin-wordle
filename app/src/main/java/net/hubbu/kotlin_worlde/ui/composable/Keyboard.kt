package net.hubbu.kotlin_worlde.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun KeyboardLetter(character: Char, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .defaultMinSize(minWidth = 18.dp)
            .background(color = Color.LightGray)
            .wrapContentSize(),
    ) {
        Text(
            text = "$character",
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier,
        )
    }
}

@Composable
fun Keyboard(modifier: Modifier = Modifier) {
    @Composable
    fun KeyRow(keys: String) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            for (char in keys) {
                KeyboardLetter(character = char)
            }
        }
    }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        KeyRow("QWERTYUIOP")
        KeyRow("ASDFGHJKL")
        KeyRow("ZXCVBNM")
    }
}

@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    Keyboard()
}
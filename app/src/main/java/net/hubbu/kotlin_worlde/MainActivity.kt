package net.hubbu.kotlin_worlde

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.hubbu.kotlin_worlde.ui.theme.KotlinWorldeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinWorldeTheme {
                GameScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Letter(character: Char, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(64.dp)
            .height(64.dp)
            .border(width = 2.dp, color = Color.LightGray)
            .wrapContentSize(),
    ) {
        Text(
            text = "$character",
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier,
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
fun Board(words: List<String>, modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        for (word in words) {
            Word("hello")
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier,
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Wordle")
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Instructions"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column {
            Board(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                words = listOf(
                    "hello",
                    "hello",
                    "hello",
                    "hello",
                    "hello",
                    "hello",
                ))
            Keyboard(modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    KotlinWorldeTheme {
        GameScreen(modifier = Modifier.fillMaxSize())
    }
}
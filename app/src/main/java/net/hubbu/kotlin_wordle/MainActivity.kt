package net.hubbu.kotlin_wordle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import net.hubbu.kotlin_wordle.ui.composable.GameScreen
import net.hubbu.kotlin_wordle.ui.theme.KotlinWordleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinWordleTheme {
                GameScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}


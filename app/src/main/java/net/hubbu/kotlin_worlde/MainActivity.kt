package net.hubbu.kotlin_worlde

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import net.hubbu.kotlin_worlde.ui.composable.GameScreen
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


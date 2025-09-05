package net.hubbu.kotlin_wordle.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TODO: Replace
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

enum class GameColor {
    Transparent, MaterialPrimary, Grey, Green, Yellow
}

@Composable
fun GameColor.getColor() : Color = when (this) {
    GameColor.Grey -> Color(0xFFD2D5DA)
    GameColor.Green -> Color(0xFF6CA965)
    GameColor.Yellow -> Color(0xFFC8B653)
    GameColor.Transparent -> Color.Transparent
    GameColor.MaterialPrimary -> MaterialTheme.colorScheme.primaryContainer
}

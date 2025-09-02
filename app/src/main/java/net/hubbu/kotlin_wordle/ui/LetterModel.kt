package net.hubbu.kotlin_wordle.ui

import net.hubbu.kotlin_wordle.ui.composable.LetterType
import net.hubbu.kotlin_wordle.ui.theme.GameColor

sealed class LetterModel(
    open val char: Char,
    open val bgColor: GameColor,
    open val borderColor: GameColor,
) {
    abstract val type: LetterType

    class Empty(
        override val char: Char = ' ',
        override val bgColor: GameColor = GameColor.Transparent,
        override val borderColor: GameColor = GameColor.MaterialPrimary
    ) : LetterModel(char, bgColor, borderColor) {
        override val type: LetterType = LetterType.Empty
    }

    class Correct(
        override val char: Char,
        override val bgColor: GameColor = GameColor.Green,
        override val borderColor: GameColor = GameColor.Transparent
    ) : LetterModel(char, bgColor, borderColor) {
        override val type: LetterType = LetterType.Correct
    }

    class Absent(
        override val char: Char,
        override val bgColor: GameColor = GameColor.MaterialPrimary,
        override val borderColor: GameColor = GameColor.Transparent
    ) : LetterModel(char, bgColor, borderColor) {
        override val type: LetterType = LetterType.Absent
    }

    class Present(
        override val char: Char,
        override val bgColor: GameColor = GameColor.Yellow,
        override val borderColor: GameColor = GameColor.Transparent
    ) : LetterModel(char, bgColor, borderColor) {
        override val type: LetterType = LetterType.Present
    }

    class Guess(
        override val char: Char,
        override val bgColor: GameColor = GameColor.Transparent,
        override val borderColor: GameColor = GameColor.MaterialPrimary
    ) : LetterModel(char, bgColor, borderColor) {
        override val type: LetterType = LetterType.Guess
    }
}
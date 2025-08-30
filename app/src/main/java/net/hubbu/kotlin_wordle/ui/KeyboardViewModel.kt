package net.hubbu.kotlin_wordle.ui

import android.util.Log
import androidx.lifecycle.ViewModel

class KeyboardViewModel : ViewModel() {
    // When a text key is pressed
    fun onKeyPress(keyText: String) {
        Log.d("KeyboardViewModel", "Key pressed: $keyText")
    }

    // When the delete key is pressed
    fun onDelete() {
        Log.d("KeyboardViewModel", "Delete pressed")
    }

    // When the enter key is pressed
    fun onEnter() {
        Log.d("KeyboardViewModel", "Enter pressed")
    }
}

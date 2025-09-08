package net.hubbu.kotlin_wordle.data

import android.content.Context

interface AppContainer {
    val wordListRepo: WordListRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val wordListRepo: WordListRepository by lazy {
        WordListRepository(context)
    }
}
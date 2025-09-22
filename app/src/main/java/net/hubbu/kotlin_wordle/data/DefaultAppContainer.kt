package net.hubbu.kotlin_wordle.data

import android.content.Context
import net.hubbu.kotlin_wordle.ui.ResourceProvider

interface AppContainer {
    val wordListRepo: WordListRepository
    val resourceProvider: ResourceProvider
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val wordListRepo: WordListRepository by lazy {
        WordListRepository(context)
    }

    override val resourceProvider: ResourceProvider = ResourceProvider(context)
}
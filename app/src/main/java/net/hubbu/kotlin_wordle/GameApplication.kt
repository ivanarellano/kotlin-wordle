package net.hubbu.kotlin_wordle

import android.app.Application
import net.hubbu.kotlin_wordle.data.DefaultAppContainer

class GameApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: DefaultAppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
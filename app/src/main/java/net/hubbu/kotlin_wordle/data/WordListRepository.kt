package net.hubbu.kotlin_wordle.data

import android.content.Context
import android.util.Log
import org.json.JSONArray

class WordListRepository(private val context: Context) {
    fun loadList(fileName: String): List<String> = try {
        val jsonString =
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        List(jsonArray.length()) { i -> jsonArray.getString(i) }
    } catch (e: Exception) {
        Log.e("GameViewModel", "Error reading or parsing $fileName", e)
        emptyList()
    }
}
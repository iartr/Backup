package com.artrblog.backup

import android.content.Context
import androidx.core.content.edit
import java.io.File
import java.util.UUID

class AuthRepository(
    private val context: Context
) {

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun updateFileAndPrefs() {
        val randomUuid1 = UUID.randomUUID().toString()
        val randomUuid2 = UUID.randomUUID().toString()
        File(context.filesDir, FILE_NAME).writeText(randomUuid1)
        prefs.edit { putString("key", randomUuid2) }

        File(context.filesDir, "random.txt").writeText("random")
    }

    fun readFile() = runCatching {
        File(context.filesDir, FILE_NAME).readText()
    }.getOrDefault("empty")

    fun readPrefs() = prefs.getString("key", "empty")!!

    companion object {
        const val FILE_NAME = "auth.txt"
        const val PREF_NAME = "auth"
    }
}
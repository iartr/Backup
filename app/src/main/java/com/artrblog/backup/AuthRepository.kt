package com.artrblog.backup

import android.content.Context
import androidx.core.content.edit
import java.io.File
import java.util.UUID

class AuthRepository(
    private val context: Context
) {

    fun updateFile() {
        val randomUuid1 = UUID.randomUUID().toString()
        File(context.filesDir, FILE_NAME).writeText(randomUuid1)
    }

    fun readFile() = runCatching {
        File(context.filesDir, FILE_NAME).readText()
    }.getOrDefault("empty")

    companion object {
        const val FILE_NAME = "auth.txt"
    }
}
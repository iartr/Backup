package com.artrblog.backup

import android.app.backup.BackupAgentHelper
import android.app.backup.FileBackupHelper
import android.app.backup.SharedPreferencesBackupHelper
import java.io.File

class BackupAgentImpl : BackupAgentHelper() {

    override fun onCreate() {
        super.onCreate()
        addHelper("files", FileBackupHelper(this, AuthRepository.FILE_NAME))
        addHelper("prefs", SharedPreferencesBackupHelper(this, AuthRepository.PREF_NAME))
    }

    override fun onRestoreFinished() {
        File(noBackupFilesDir, "onRestoreFinished.txt")
            .writeText("onRestoreFinished: key-value")
    }
}
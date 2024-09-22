package com.artrblog.backup

import android.app.backup.BackupAgentHelper
import java.io.File

class AutoBackupAgentImpl : BackupAgentHelper() {

    override fun onRestoreFinished() {
        super.onRestoreFinished()
        File(noBackupFilesDir, "onRestoreFinished.txt")
            .writeText("onRestoreFinished: autobackup at ${System.currentTimeMillis()}")
    }

    override fun onQuotaExceeded(backupDataBytes: Long, quotaBytes: Long) {
        super.onQuotaExceeded(backupDataBytes, quotaBytes)
        File(noBackupFilesDir, "onQuotaExceeded.txt")
            .writeText("""
                backupDataBytes: $backupDataBytes
                quotaBytes: $quotaBytes
            """.trimIndent())
    }

}
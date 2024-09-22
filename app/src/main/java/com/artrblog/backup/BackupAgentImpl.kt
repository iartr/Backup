package com.artrblog.backup

import android.app.backup.BackupAgentHelper
import android.app.backup.BackupDataInput
import android.app.backup.BackupDataOutput
import android.os.ParcelFileDescriptor
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class BackupAgentImpl : BackupAgentHelper() {

    override fun onBackup(
        oldState: ParcelFileDescriptor?,
        data: BackupDataOutput?,
        newState: ParcelFileDescriptor?
    ) {
        val fileLastModified = File(filesDir, AuthRepository.FILE_NAME).lastModified()
        val oldStateModified = runCatching {
            DataInputStream(FileInputStream(oldState?.fileDescriptor)).use {
                it.readLong()
            }
        }.getOrDefault(0)

        // Файл не изменен, не нужно бэкапить
        if (fileLastModified == oldStateModified) {
            newState?.updateMetaInf(value = File(filesDir, AuthRepository.FILE_NAME).lastModified())
            return
        }

        val dataByteArray = File(filesDir, AuthRepository.FILE_NAME).readBytes()
        data?.writeEntityHeader(AuthRepository.FILE_NAME, dataByteArray.size)
        data?.writeEntityData(dataByteArray, dataByteArray.size)

        newState?.updateMetaInf(value = File(filesDir, AuthRepository.FILE_NAME).lastModified())
    }

    override fun onRestore(
        data: BackupDataInput?,
        appVersionCode: Int,
        newState: ParcelFileDescriptor?
    ) {
        while (data?.readNextHeader() == true) {
            when (data.key) {
                AuthRepository.FILE_NAME -> {
                    val dataByteArray = ByteArray(data.dataSize)
                    data.readEntityData(dataByteArray, 0, data.dataSize)
                    File(filesDir, AuthRepository.FILE_NAME).writeBytes(dataByteArray)
                }
                // Для примера
                "auth_database" -> {
                    val dataByteArray = ByteArray(data.dataSize)
                    data.readEntityData(dataByteArray, 0, data.dataSize)

                    // Записать dataByteArray в базу данных
                }
                else -> Unit
            }
        }
    }

    private fun ParcelFileDescriptor.updateMetaInf(value: Long) {
        DataOutputStream(FileOutputStream(this.fileDescriptor)).use {
            it.writeLong(value)
        }
    }

    override fun onRestoreFinished() {
        File(noBackupFilesDir, "onRestoreFinished.txt")
            .writeText("onRestoreFinished: key-value custom")
    }
}
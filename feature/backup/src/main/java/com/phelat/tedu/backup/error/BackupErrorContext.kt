package com.phelat.tedu.backup.error

sealed class BackupErrorContext : Throwable() {

    object GetFileFailed : BackupErrorContext()

    object UpdateFileFailed : BackupErrorContext()

    object CorruptedFile : BackupErrorContext()

    object FileNotFound : BackupErrorContext()

    object CredentialsEmpty : BackupErrorContext()

    object LocalUpdateFailed : BackupErrorContext()
}
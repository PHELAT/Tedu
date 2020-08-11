package com.phelat.tedu.backup.error

sealed class BackupErrorContext : Throwable() {

    class GetFileFailed : BackupErrorContext()

    class UpdateFileFailed : BackupErrorContext()

    class CorruptedFile : BackupErrorContext()

    class FileNotFound : BackupErrorContext()

    class CredentialsEmpty : BackupErrorContext()

    class LocalUpdateFailed : BackupErrorContext()

    class UrlDoesNotMatchPattern : BackupErrorContext()
}
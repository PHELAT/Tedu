package com.phelat.tedu.backup.error

sealed class WebDavErrorContext {

    object GetFileFailed : WebDavErrorContext()

    object CorruptedFile : WebDavErrorContext()

    object FileNotFound : WebDavErrorContext()

    object CredentialsEmpty : WebDavErrorContext()
}
package com.phelat.tedu.backup.repository

import com.phelat.tedu.backup.entity.BackupTodoEntity
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.WebDavErrorContext
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Response
import javax.inject.Inject

@CommonScope
internal class WebDavRepository @Inject constructor(
    private val credentialsReadable: Readable<WebDavCredentials>,
    private val webDavReadable: Readable.IO<WebDavCredentials, Response<BackupTodoEntity, WebDavErrorContext>>
) : Readable<@JvmSuppressWildcards Response<BackupTodoEntity, WebDavErrorContext>> {

    override fun read(): Response<BackupTodoEntity, WebDavErrorContext> {
        return credentialsReadable.read()
            .let(webDavReadable::read)
    }
}
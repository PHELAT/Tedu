package com.phelat.tedu.backup.repository

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.entity.BackupTodoEntity
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.getFailureResponse
import com.phelat.tedu.functional.ifSuccessful
import javax.inject.Inject

@FeatureScope
internal class WebDavSyncRepository @Inject constructor(
    private val credentialsReadable: Readable<Response<WebDavCredentials, BackupErrorContext>>,
    private val webDavReadable: Readable.IO<WebDavCredentials, Response<List<BackupTodoEntity>, BackupErrorContext>>
) : BackupSyncRepository {

    override suspend fun sync(): Response<List<BackupTodoEntity>, BackupErrorContext> {
        val credentialsResponse = credentialsReadable.read()
        credentialsResponse.ifSuccessful { credentials ->
            return webDavReadable.read(credentials)
        }
        return credentialsResponse.getFailureResponse()
    }
}
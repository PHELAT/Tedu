package com.phelat.tedu.backup.repository

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import com.phelat.tedu.functional.getFailureResponse
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.todo.entity.ActionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

@FeatureScope
internal class WebDavSyncRepository @Inject constructor(
    private val credentialsReadable: Readable<Response<WebDavCredentials, BackupErrorContext>>,
    private val webDavReadable: Readable.IO<WebDavCredentials, Response<List<ActionEntity>, BackupErrorContext>>,
    private val actionsReadable: Readable<Flow<List<ActionEntity>>>
) : BackupSyncRepository {

    override suspend fun sync(): Response<List<ActionEntity>, BackupErrorContext> {
        val credentialsResponse = credentialsReadable.read()
        credentialsResponse.ifSuccessful { credentials ->
            webDavReadable.read(credentials).ifSuccessful { remoteActions ->
                return Success(getUnSavedActions(remoteActions))
            }
        }
        return credentialsResponse.getFailureResponse()
    }

    private suspend fun getUnSavedActions(remoteActions: List<ActionEntity>): List<ActionEntity> {
        val unSavedActions = mutableListOf<ActionEntity>()
        val savedActions = HashMap<Long, ActionEntity>()
        actionsReadable.read()
            .single()
            .sorted()
            .forEach { action -> savedActions += action.timestamp to action }
        remoteActions.forEach { remoteAction ->
            if (savedActions.containsKey(remoteAction.timestamp).not()) {
                unSavedActions.add(remoteAction)
            }
        }
        return unSavedActions
    }
}
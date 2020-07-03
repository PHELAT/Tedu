package com.phelat.tedu.backup.repository

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.entity.ActionStatusEntity
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.backup.request.WriteWebDavRequest
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import com.phelat.tedu.functional.getFailureResponse
import com.phelat.tedu.functional.getSuccessResponse
import com.phelat.tedu.functional.ifNotSuccessful
import com.phelat.tedu.functional.isSuccessful
import com.phelat.tedu.todo.entity.ActionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@FeatureScope
internal class WebDavSyncRepository @Inject constructor(
    private val credentialsReadable: Readable<Response<WebDavCredentials, BackupErrorContext>>,
    private val webDavReadable: Readable.IO<WebDavCredentials, Response<List<ActionEntity>, BackupErrorContext>>,
    private val webDavWritable: Writable.IO<WriteWebDavRequest, Response<Unit, BackupErrorContext>>,
    private val actionsReadable: Readable<Flow<List<ActionEntity>>>
) : BackupSyncRepository {

    override suspend fun sync(
        createIfNotExists: Boolean
    ): Response<List<ActionStatusEntity>, BackupErrorContext> {
        return credentialsReadable.read().run {
            if (isSuccessful()) {
                val credentials = getSuccessResponse().value
                val response = readWebDav(credentials)
                processWebDavResponse(createIfNotExists, response, credentials)
            } else {
                getFailureResponse()
            }
        }
    }

    private suspend fun processWebDavResponse(
        createIfNotExists: Boolean,
        response: Response<List<ActionStatusEntity>, BackupErrorContext>,
        credentials: WebDavCredentials
    ): Response<List<ActionStatusEntity>, BackupErrorContext> {
        response.ifNotSuccessful { error ->
            if (createIfNotExists && error is BackupErrorContext.FileNotFound) {
                val localActions = actionsReadable.read().firstOrNull() ?: emptyList()
                val allActions = combineRemoteAndLocalActions(emptyList(), localActions)
                val updateResponse = updateRemoteActions(allActions, credentials)
                return if (updateResponse.isSuccessful()) {
                    Success(emptyList())
                } else {
                    updateResponse.getFailureResponse()
                }
            }
        }
        return response
    }

    private suspend fun readWebDav(
        credentials: WebDavCredentials
    ): Response<List<ActionStatusEntity>, BackupErrorContext> {
        return webDavReadable.read(credentials).run {
            if (isSuccessful()) {
                val remoteActions = getSuccessResponse().value
                val localActions = actionsReadable.read().firstOrNull() ?: emptyList()
                val allActions = combineRemoteAndLocalActions(remoteActions, localActions)
                val updateResponse = updateRemoteActions(allActions, credentials)
                if (updateResponse.isSuccessful()) {
                    Success(allActions)
                } else {
                    updateResponse.getFailureResponse()
                }
            } else {
                getFailureResponse()
            }
        }
    }

    private fun combineRemoteAndLocalActions(
        remoteActions: List<ActionEntity>,
        localActions: List<ActionEntity>
    ): List<ActionStatusEntity> {
        val combinedActions = LinkedHashMap<Long, ActionStatusEntity>()
        localActions.forEach { action ->
            combinedActions[action.timestamp] = ActionStatusEntity(
                isLocallySaved = true,
                data = action
            )
        }
        remoteActions.forEach { remoteAction ->
            if (combinedActions.containsKey(remoteAction.timestamp).not()) {
                combinedActions[remoteAction.timestamp] = ActionStatusEntity(
                    isLocallySaved = false,
                    data = remoteAction
                )
            }
        }
        return combinedActions.values.sortedBy { entity -> entity.data }
    }

    private fun updateRemoteActions(
        allActions: List<ActionStatusEntity>,
        credentials: WebDavCredentials
    ): Response<Unit, BackupErrorContext> {
        val actions = allActions.map { entity -> entity.data }
        val writeRequest = WriteWebDavRequest(actions, credentials)
        return webDavWritable.write(writeRequest)
    }
}
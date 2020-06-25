package com.phelat.tedu.backup.usecase

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.entity.ActionStatusEntity
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.Failure
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import com.phelat.tedu.functional.getFailureResponse
import com.phelat.tedu.functional.getSuccessResponse
import com.phelat.tedu.functional.ifNotSuccessful
import com.phelat.tedu.functional.isSuccessful
import com.phelat.tedu.todo.repository.TodoRepository
import javax.inject.Inject

@FeatureScope
class WebDavBackupUseCase @Inject constructor(
    private val webDavBackupRepository: BackupSyncRepository,
    private val todoRepository: TodoRepository
) : BackupUseCase {

    override suspend fun sync(): Response<Unit, BackupErrorContext> {
        return webDavBackupRepository.sync().run {
            if (isSuccessful()) {
                handleSuccessfulCase(getSuccessResponse().value)
            } else {
                getFailureResponse()
            }
        }
    }

    private suspend fun handleSuccessfulCase(
        response: List<ActionStatusEntity>
    ): Response<Unit, BackupErrorContext> {
        for (action in response) {
            if (action.isLocallySaved.not()) {
                todoRepository.processAction(action.data)
                    .ifNotSuccessful {
                        return Failure(BackupErrorContext.LocalUpdateFailed)
                    }
            }
        }
        return Success(Unit)
    }
}
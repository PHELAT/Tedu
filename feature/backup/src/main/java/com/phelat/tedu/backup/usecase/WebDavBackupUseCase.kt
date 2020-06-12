package com.phelat.tedu.backup.usecase

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.functional.otherwise
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.repository.TodoRepository
import javax.inject.Inject

@FeatureScope
class WebDavBackupUseCase @Inject constructor(
    private val webDavBackupRepository: BackupSyncRepository,
    private val todoRepository: TodoRepository
) : BackupUseCase {

    override suspend fun sync() {
        webDavBackupRepository.sync()
            .ifSuccessful { response -> handleSuccessfulCase(response) }
            .otherwise { error -> handleErrorCase(error) }
    }

    private suspend fun handleSuccessfulCase(response: List<ActionEntity>) {
        response.forEach { entity -> todoRepository.processAction(entity) }
    }

    private fun handleErrorCase(error: BackupErrorContext) {
        // TODO: handle error properly
        println(error)
    }
}
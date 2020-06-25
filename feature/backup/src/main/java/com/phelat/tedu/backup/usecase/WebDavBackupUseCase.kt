package com.phelat.tedu.backup.usecase

import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.analytics.di.qualifier.Development
import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.entity.ActionStatusEntity
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.ifNotSuccessful
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.functional.otherwise
import com.phelat.tedu.todo.repository.TodoRepository
import javax.inject.Inject

@FeatureScope
class WebDavBackupUseCase @Inject constructor(
    private val webDavBackupRepository: BackupSyncRepository,
    private val todoRepository: TodoRepository,
    @Development private val logger: ExceptionLogger
) : BackupUseCase {

    override suspend fun sync() {
        webDavBackupRepository.sync()
            .ifSuccessful { response -> handleSuccessfulCase(response) }
            .otherwise(logger::log)
    }

    private suspend fun handleSuccessfulCase(response: List<ActionStatusEntity>) {
        response.forEach { entity ->
            if (entity.isLocallySaved.not()) {
                todoRepository.processAction(entity.data)
                    .ifNotSuccessful(logger::log)
            }
        }
    }
}
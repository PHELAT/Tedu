package com.phelat.tedu.backup.usecase

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.functional.otherwise
import com.phelat.tedu.todo.entity.Action
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.repository.TodoRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@FeatureScope
class BackupUseCase @Inject constructor(
    private val webDavBackupRepository: BackupSyncRepository,
    private val todoRepository: TodoRepository,
    private val dispatcher: Dispatcher
) {

    private val scope = MainScope()

    fun command() {
        scope.launch(context = dispatcher.iO) {
            webDavBackupRepository.sync()
                .ifSuccessful { response -> handleSuccessfulCase(response) }
                .otherwise { error -> handleErrorCase(error) }
        }
    }

    private suspend fun handleSuccessfulCase(response: List<ActionEntity>) {
        response.forEach { entity ->
            when (entity.action) {
                Action.Add -> {
                    todoRepository.addTodo(entity.data)
                }
                Action.Delete -> {
                    todoRepository.deleteTodo(entity.data)
                }
                Action.Update -> {
                    todoRepository.updateTodo(entity.data)
                }
            }
        }
    }

    private fun handleErrorCase(error: BackupErrorContext) {
        println(error)
    }
}
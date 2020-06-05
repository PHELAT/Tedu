package com.phelat.tedu.backup.usecase

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.entity.BackupTodoEntity
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.functional.otherwise
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.repository.TodoRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Date
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

    private suspend fun handleSuccessfulCase(response: List<BackupTodoEntity>) {
        response.forEach { entity ->
            val convertedData = convertRawDataToTodoEntity(entity.data)
            when (entity.action) {
                "add" -> {
                    todoRepository.addTodo(convertedData)
                }
                "delete" -> {
                    todoRepository.deleteTodo(convertedData)
                }
                "update" -> {
                    todoRepository.updateTodo(convertedData)
                }
            }
        }
    }

    private fun convertRawDataToTodoEntity(raw: JSONObject): TodoEntity {
        return TodoEntity(
            todo = raw.getString("todo"),
            todoId = raw.getInt("todoId"),
            isDone = raw.getBoolean("isDone"),
            date = Date(raw.getLong("date"))
        )
    }

    private fun handleErrorCase(error: BackupErrorContext) {
        println(error)
    }
}
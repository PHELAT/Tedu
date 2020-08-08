package com.phelat.tedu.backup.datasource

import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.backup.request.WriteWebDavRequest
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.Failure
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import com.phelat.tedu.sdkextensions.isValidUrlWithProtocol
import com.phelat.tedu.todo.entity.Action
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.entity.TodoEntity
import com.thegrizzlylabs.sardineandroid.Sardine
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.Date
import java.util.Scanner
import javax.inject.Inject

@FeatureScope
internal class WebDavDataSource @Inject constructor(
    private val sardine: Sardine
) : WebDavReadable,
    WebDavWritable {

    override fun read(
        input: WebDavCredentials
    ): Response<List<ActionEntity>, BackupErrorContext> {
        if (input.url.isValidUrlWithProtocol().not()) {
            return Failure(BackupErrorContext.UrlDoesNotMatchPattern())
        }
        sardine.setCredentials(input.username, input.password)
        return try {
            val url = getNormalizedUrl(input.url) + TEDU_BACKUP_FILE
            if (sardine.exists(url)) {
                sardine.get(url)
                    .use(::inputStreamToJsonObject)
                    .let(::backupContentToBackupEntity)
                    .let(::Success)
            } else {
                Failure(BackupErrorContext.FileNotFound())
            }
        } catch (ignore: IOException) {
            Failure(BackupErrorContext.GetFileFailed())
        } catch (ignore: JSONException) {
            Failure(BackupErrorContext.CorruptedFile())
        }
    }

    override fun write(input: WriteWebDavRequest): Response<Unit, BackupErrorContext> {
        if (input.credentials.url.isValidUrlWithProtocol().not()) {
            return Failure(BackupErrorContext.UrlDoesNotMatchPattern())
        }
        sardine.setCredentials(input.credentials.username, input.credentials.password)
        val content = actionEntityToByteArray(input.entities)
        val url = getNormalizedUrl(input.credentials.url) + TEDU_BACKUP_FILE
        return try {
            sardine.put(url, content)
            Success(Unit)
        } catch (ignore: IOException) {
            Failure(BackupErrorContext.UpdateFileFailed())
        }
    }

    private fun actionEntityToByteArray(entities: List<ActionEntity>): ByteArray {
        val actions = JSONArray()
        entities.forEach { entity ->
            val action = JSONObject().apply {
                put(ACTION_KEY, entity.action.actionName)
                put(TIME_STAMP_KEY, entity.timestamp)
                val data = JSONObject().apply {
                    put(TODO_ID_KEY, entity.data.todoId)
                    put(TODO_KEY, entity.data.todo)
                    put(IS_DONE_KEY, entity.data.isDone)
                    put(DATE_KEY, entity.data.date.time)
                }
                put(DATA_KEY, data)
            }
            actions.put(action)
        }
        val root = JSONObject().apply {
            put(ACTIONS_KEY, actions)
        }
        return root.toString().toByteArray()
    }

    private fun getNormalizedUrl(url: String): String {
        return if (url.last() == '/') {
            url
        } else {
            "$url/"
        }
    }

    private fun inputStreamToJsonObject(inputStream: InputStream): JSONObject {
        val scanner = Scanner(inputStream).useDelimiter("\\A")
        val builder = StringBuilder()
        while (scanner.hasNext()) {
            builder.append(scanner.next())
        }
        return JSONObject(builder.toString())
    }

    private fun backupContentToBackupEntity(content: JSONObject): List<ActionEntity> {
        val doneActions = mutableListOf<ActionEntity>()
        val actions = content.getJSONArray(ACTIONS_KEY)
        for (i in 0 until actions.length()) {
            val currentAction = actions.getJSONObject(i)
            val actionType = getActionType(currentAction.getString(ACTION_KEY))
            val actionTimestamp = currentAction.getLong(TIME_STAMP_KEY)
            val actionData = convertRawDataToTodoEntity(currentAction.getJSONObject(DATA_KEY))
            doneActions += ActionEntity(actionType, actionTimestamp, actionData)
        }
        doneActions.sort()
        return doneActions
    }

    private fun getActionType(action: String): Action {
        return when (action) {
            Action.Add.actionName -> Action.Add
            Action.Update.actionName -> Action.Update
            Action.Delete.actionName -> Action.Delete
            else -> Action.Undefined
        }
    }

    private fun convertRawDataToTodoEntity(raw: JSONObject): TodoEntity {
        return TodoEntity(
            todo = raw.getString(TODO_KEY),
            todoId = raw.getLong(TODO_ID_KEY),
            isDone = raw.getBoolean(IS_DONE_KEY),
            date = Date(raw.getLong(DATE_KEY))
        )
    }

    companion object {
        private const val TEDU_BACKUP_FILE = "tedu.backup"
        private const val ACTION_KEY = "action"
        private const val TIME_STAMP_KEY = "timestamp"
        private const val TODO_ID_KEY = "todoId"
        private const val TODO_KEY = "todo"
        private const val IS_DONE_KEY = "isDone"
        private const val DATE_KEY = "date"
        private const val DATA_KEY = "data"
        private const val ACTIONS_KEY = "actions"
    }
}

typealias WebDavReadable = Readable.IO<WebDavCredentials,
        @JvmSuppressWildcards Response<List<ActionEntity>, BackupErrorContext>>

typealias WebDavWritable = Writable.IO<WriteWebDavRequest,
        @JvmSuppressWildcards Response<Unit, BackupErrorContext>>
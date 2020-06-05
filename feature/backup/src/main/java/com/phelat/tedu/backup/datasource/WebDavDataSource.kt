package com.phelat.tedu.backup.datasource

import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.Failure
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import com.phelat.tedu.todo.entity.Action
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.entity.TodoEntity
import com.thegrizzlylabs.sardineandroid.Sardine
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
) : Readable.IO<WebDavCredentials, @JvmSuppressWildcards Response<List<ActionEntity>, BackupErrorContext>> {

    override fun read(
        input: WebDavCredentials
    ): Response<List<ActionEntity>, BackupErrorContext> {
        sardine.setCredentials(input.username, input.password)
        return try {
            val url = getNormalizedUrl(input.url) + TEDU_BACKUP_FILE
            if (sardine.exists(url)) {
                sardine.get(url)
                    .use(::inputStreamToJsonObject)
                    .let(::backupContentToBackupEntity)
                    .let(::Success)
            } else {
                Failure(BackupErrorContext.FileNotFound)
            }
        } catch (ignore: IOException) {
            Failure(BackupErrorContext.GetFileFailed)
        } catch (ignore: JSONException) {
            Failure(BackupErrorContext.CorruptedFile)
        }
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
        val actions = content.getJSONArray("actions")
        for (i in 0 until actions.length()) {
            val currentAction = actions.getJSONObject(i)
            val actionType = getActionType(currentAction.getString("action"))
            val actionTimestamp = currentAction.getLong("timestamp")
            val actionData = convertRawDataToTodoEntity(currentAction.getJSONObject("data"))
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
            todo = raw.getString("todo"),
            todoId = raw.getInt("todoId"),
            isDone = raw.getBoolean("isDone"),
            date = Date(raw.getLong("date"))
        )
    }

    companion object {
        private const val TEDU_BACKUP_FILE = "tedu.backup"
    }
}
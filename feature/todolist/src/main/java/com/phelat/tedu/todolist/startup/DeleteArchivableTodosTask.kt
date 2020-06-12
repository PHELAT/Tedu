package com.phelat.tedu.todolist.startup

import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.analytics.di.qualifier.NonFatal
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.ifNotSuccessful
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.functional.otherwise
import com.phelat.tedu.todo.entity.Action
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.error.TodoArchivableErrorContext
import com.phelat.tedu.todo.error.TodoErrorContext
import com.phelat.tedu.todo.type.ArchivableTodos
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@FeatureScope
class DeleteArchivableTodosTask @Inject constructor(
    private val archivableTodoDataSourceReadable: Readable.Suspendable.IO<Date, ArchivableTodos>,
    private val archivableTodoDataSourceDeletable: Deletable.Suspendable.IO<ArchivableTodos, Response<Unit, TodoArchivableErrorContext>>,
    private val dateDataSourceReadable: Readable.IO<TeduDate, Date>,
    private val actionWritable: Writable.Suspendable.IO<ActionEntity, Response<Unit, TodoErrorContext>>,
    private val dispatcher: Dispatcher,
    @NonFatal private val exceptionLogger: ExceptionLogger
) : Runnable {

    private val mainScope = MainScope()

    override fun run() {
        mainScope.launch(context = dispatcher.iO) {
            val today = dateDataSourceReadable.read(TeduDate.Today)
            val todosToDelete = archivableTodoDataSourceReadable.read(today)
            if (todosToDelete.isNotEmpty()) {
                archivableTodoDataSourceDeletable.delete(todosToDelete)
                    .ifSuccessful { insertDeletionAction(todosToDelete) }
                    .otherwise(exceptionLogger::log)
            }
        }
    }

    private suspend fun insertDeletionAction(archivedTodos: ArchivableTodos) {
        for (todo in archivedTodos) {
            val action = ActionEntity(
                action = Action.Delete,
                timestamp = System.currentTimeMillis(),
                data = todo
            )
            actionWritable.write(action)
                .ifNotSuccessful(exceptionLogger::log)
        }
    }
}
package com.phelat.tedu.backup

import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.functional.Response
import com.phelat.tedu.todo.entity.ActionEntity

interface BackupSyncRepository {

    suspend fun sync(): Response<List<ActionEntity>, BackupErrorContext>
}
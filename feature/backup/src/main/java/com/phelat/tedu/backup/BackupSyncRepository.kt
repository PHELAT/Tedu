package com.phelat.tedu.backup

import com.phelat.tedu.backup.entity.BackupTodoEntity
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.functional.Response

interface BackupSyncRepository {

    suspend fun sync(): Response<List<BackupTodoEntity>, BackupErrorContext>
}
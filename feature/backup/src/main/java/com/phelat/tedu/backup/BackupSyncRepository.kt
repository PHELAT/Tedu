package com.phelat.tedu.backup

import com.phelat.tedu.backup.entity.ActionStatusEntity
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.functional.Response

interface BackupSyncRepository {

    suspend fun sync(
        createIfNotExists: Boolean
    ): Response<List<ActionStatusEntity>, BackupErrorContext>
}
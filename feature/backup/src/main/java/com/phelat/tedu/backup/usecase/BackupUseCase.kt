package com.phelat.tedu.backup.usecase

import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.functional.Response

interface BackupUseCase {

    suspend fun sync(createIfNotExists: Boolean): Response<Unit, BackupErrorContext>
}
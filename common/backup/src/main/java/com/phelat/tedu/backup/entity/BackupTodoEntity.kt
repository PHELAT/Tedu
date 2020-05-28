package com.phelat.tedu.backup.entity

import org.json.JSONObject

data class BackupTodoEntity(
    val backupVersion: Int,
    val todos: JSONObject
)
package com.phelat.tedu.backup.entity

import org.json.JSONObject

data class BackupTodoEntity(
    val action: String,
    val data: JSONObject
)
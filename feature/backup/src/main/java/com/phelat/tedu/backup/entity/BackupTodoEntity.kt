package com.phelat.tedu.backup.entity

import org.json.JSONObject

data class BackupTodoEntity(
    val action: String,
    val timestamp: Long,
    val data: JSONObject
): Comparable<BackupTodoEntity> {

    override fun compareTo(other: BackupTodoEntity): Int {
        return when {
            timestamp > other.timestamp -> {
                1
            }
            timestamp == other.timestamp -> {
                0
            }
            else -> {
                -1
            }
        }
    }
}
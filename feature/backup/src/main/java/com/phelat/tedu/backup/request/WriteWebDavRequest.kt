package com.phelat.tedu.backup.request

import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.todo.entity.ActionEntity

data class WriteWebDavRequest(
    val entities: List<ActionEntity>,
    val credentials: WebDavCredentials
)
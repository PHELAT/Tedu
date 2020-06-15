package com.phelat.tedu.backup.entity

import com.phelat.tedu.todo.entity.ActionEntity

data class ActionStatusEntity(
    val isLocallySaved: Boolean,
    val data: ActionEntity
)
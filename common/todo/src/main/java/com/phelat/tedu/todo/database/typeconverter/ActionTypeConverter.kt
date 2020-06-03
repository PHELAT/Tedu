package com.phelat.tedu.todo.database.typeconverter

import androidx.room.TypeConverter
import com.phelat.tedu.todo.entity.Action

class ActionTypeConverter {

    @TypeConverter
    fun toAction(action: String): Action {
        return when (action) {
            Action.Add.actionName -> Action.Add
            Action.Update.actionName -> Action.Update
            Action.Delete.actionName -> Action.Delete
            else -> Action.Undefined
        }
    }

    @TypeConverter
    fun toString(action: Action): String {
        return action.actionName
    }
}
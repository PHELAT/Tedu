package com.phelat.tedu.todo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.phelat.tedu.todo.database.entity.ActionDatabaseEntity

@Dao
interface ActionEntityDao {

    @Insert(entity = ActionDatabaseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAction(action: ActionDatabaseEntity)
}
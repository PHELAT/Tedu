package com.phelat.tedu.todo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phelat.tedu.todo.database.entity.ActionDatabaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ActionEntityDao {

    @Insert(entity = ActionDatabaseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAction(action: ActionDatabaseEntity): Long

    @Query("SELECT * FROM ACTION_TABLE")
    fun queryActions(): Flow<List<ActionDatabaseEntity>>
}
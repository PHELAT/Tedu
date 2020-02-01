package com.phelat.tedu.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity

@Database(entities = [TodoDatabaseEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoEntityDao(): TodoEntityDao
}
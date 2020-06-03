package com.phelat.tedu.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.phelat.tedu.todo.database.dao.ActionEntityDao
import com.phelat.tedu.todo.database.dao.TodoEntityDao
import com.phelat.tedu.todo.database.entity.ActionDatabaseEntity
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.database.typeconverter.ActionTypeConverter
import com.phelat.tedu.todo.database.typeconverter.DateTypeConverter

@Database(entities = [TodoDatabaseEntity::class, ActionDatabaseEntity::class], version = 1)
@TypeConverters(DateTypeConverter::class, ActionTypeConverter::class)
internal abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoEntityDao(): TodoEntityDao

    abstract fun actionEntityDao(): ActionEntityDao
}
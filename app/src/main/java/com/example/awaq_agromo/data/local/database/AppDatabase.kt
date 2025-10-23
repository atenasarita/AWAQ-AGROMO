package com.example.awaq_agromo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.awaq_agromo.data.local.dao.FormDao
import com.example.awaq_agromo.data.local.entity.FormEntity

@Database(
    entities = [FormEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun FormDao(): FormDao
}

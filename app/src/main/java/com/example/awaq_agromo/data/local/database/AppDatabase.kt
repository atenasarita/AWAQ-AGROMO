package com.example.awaq_agromo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.awaq_agromo.data.local.db.VariedadDao
import com.example.awaq_agromo.data.local.daos.HumedadDao
import com.example.awaq_agromo.data.local.entity.VariedadEntity
import com.example.awaq_agromo.data.local.entity.HumedadEntity

@Database(
    entities = [
        VariedadEntity::class,
        HumedadEntity::class
        // later you can add: ConditionsEntity, MonitoreoEntity, etc.
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun variedadDao(): VariedadDao
    abstract fun humedadDao(): HumedadDao
}


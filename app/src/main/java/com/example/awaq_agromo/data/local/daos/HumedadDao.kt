package com.example.awaq_agromo.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.awaq_agromo.data.local.entity.HumedadEntity

@Dao
interface HumedadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHumedad(humedad: HumedadEntity)

    @Query("SELECT * FROM humedad LIMIT 1")
    fun getLastHumedad(): Flow<HumedadEntity?>

    @Query("DELETE FROM humedad")
    suspend fun clearHumedad()
}

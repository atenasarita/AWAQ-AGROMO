package com.example.awaq_agromo.data.local.db

import androidx.room.*
import com.example.awaq_agromo.data.local.entity.VariedadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VariedadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForm(form: VariedadEntity)

    @Query("SELECT * FROM variedad_form WHERE userId = :userId")
    fun getFormsForUser(userId: Int): Flow<List<VariedadEntity>>

    @Query("DELETE FROM variedad_form WHERE userId = :userId")
    suspend fun deleteFormsForUser(userId: Int)
}
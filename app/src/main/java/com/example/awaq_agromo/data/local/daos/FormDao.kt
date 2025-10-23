package com.example.awaq_agromo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.awaq_agromo.data.local.entity.FormEntity

@Dao
interface FormDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(form: FormEntity): Long

    @Update
    suspend fun update(form: FormEntity)

    @Query("SELECT * FROM form_data WHERE id = :id")
    suspend fun getFormById(id: Long): FormEntity?

    @Query("SELECT * FROM form_data")
    suspend fun getAllForms(): List<FormEntity>
}

package com.example.awaq_agromo.data.local.repository


import com.example.awaq_agromo.data.local.daos.HumedadDao
import com.example.awaq_agromo.data.local.db.VariedadDao
import com.example.awaq_agromo.data.local.entity.HumedadEntity
import com.example.awaq_agromo.data.local.entity.VariedadEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VariedadRepository @Inject constructor(
    private val dao: VariedadDao
) {
    suspend fun insertForm(form: VariedadEntity) = dao.insertForm(form)
    fun getForms(userId: Int): Flow<List<VariedadEntity>> = dao.getFormsForUser(userId)
    suspend fun deleteForms(userId: Int) = dao.deleteFormsForUser(userId)
}

class HumedadRepository @Inject constructor(
    private val dao: HumedadDao
) {
    suspend fun saveHumedad(desc: String, value: Int) {
        dao.insertHumedad(HumedadEntity(humedadDesc = desc, humedadValue = value))
    }

    fun getLastHumedad(): Flow<HumedadEntity?> = dao.getLastHumedad()

    suspend fun clearHumedad() = dao.clearHumedad()
}
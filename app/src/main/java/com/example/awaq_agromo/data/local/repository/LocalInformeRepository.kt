package com.example.awaq_agromo.data.local.repository

import com.example.awaq_agromo.data.local.dao.FormDao
import com.example.awaq_agromo.data.local.entity.FormEntity

class FormRepository(private val dao: FormDao) {

    suspend fun upsertPartialForm(
        id: Int? = null,
        userId: Int? = null,
        selectedCrops: String? = null,
        sowingDate: String? = null,     // keep as String if you want, or convert to Long for Date
        humedadDesc: String? = null,
        humedadValue: Int? = null
    ) {
        if (id != null) {
            val current = dao.getFormById(id.toLong())
            if (current != null) {
                val updated = current.copy(
                    userId = userId ?: current.userId,
                    selectedCrops = selectedCrops ?: current.selectedCrops,
                    sowingDate = sowingDate ?: current.sowingDate,
                    humedadDesc = humedadDesc ?: current.humedadDesc,
                    humedadValue = humedadValue ?: current.humedadValue
                )
                dao.update(updated)
                return
            }
        }

       val newForm = FormEntity(
            userId = userId,
            selectedCrops = selectedCrops,
            sowingDate = sowingDate,
            humedadDesc = humedadDesc,
            humedadValue = humedadValue
        )
        dao.insert(newForm)
    }

    suspend fun getFormById(id: Int): FormEntity? = dao.getFormById(id.toLong())
}

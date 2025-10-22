package com.example.awaq_agromo.di

import com.example.awaq_agromo.data.local.store.CultivosUsuario
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DataStoreEntryPoint {
    fun cultivosUsuario(): CultivosUsuario
}

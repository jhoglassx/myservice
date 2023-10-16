package com.jhoglas.mysalon.data.remote

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {

    suspend fun setUser(userId: String, userRemoteEntity: UserRemoteEntity): Flow<Boolean>
    suspend fun getUser(userId: String): Flow<UserRemoteEntity>
}
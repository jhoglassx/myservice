package com.jhoglas.mysalon.data.remote

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val database: FirebaseDatabase
) : AuthDataSource {

    override suspend fun setUser(userId: String, userRemoteEntity: UserRemoteEntity): Flow<Boolean> = flow {
        val databaseReference = database.getReference("accounts").child(userId)

        val result = databaseReference.setValue(userRemoteEntity).isSuccessful

        emit(result)
    }

    override suspend fun getUser(userId: String): Flow<UserRemoteEntity> = flow {
        val data = database.getReference("accounts").child(userId).get().await()

        emit(
            UserRemoteEntity(
                name = data.child("name")?.value.toString(),
                email = data.child("email")?.value.toString(),
                phoneNumber = data.child("phoneNumber")?.value.toString(),
                image = data.child("image")?.value.toString(),
                dateCreate = data.child("dateCreate")?.value.toString(),
                dateUpdate = data.child("dateUpdate")?.value.toString()
            )
        )
    }
}

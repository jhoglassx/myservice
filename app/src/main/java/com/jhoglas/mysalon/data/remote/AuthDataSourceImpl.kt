package com.jhoglas.mysalon.data.remote

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthDataSourceImpl @Inject constructor(
    private val database: FirebaseDatabase
) : AuthDataSource {

    override suspend fun setUser(userId: String, userRemoteEntity: UserRemoteEntity): Flow<Boolean> = flow {
        val databaseReference = database.getReference("accounts").child(userId)

        val result = suspendCoroutine { continuation ->
            databaseReference.setValue(userRemoteEntity).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }
        }

        emit(result)
    }

    override suspend fun getUser(userId: String): Flow<UserRemoteEntity> = flow {
        val databaseReference = database.getReference("accounts").child(userId)

        val result = suspendCoroutine { continuation ->
            databaseReference.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(task.result)
                } else {
                    continuation.resume(task.result)
                }
            }
        }

        emit(
            UserRemoteEntity(
                name = result.child("name")?.value.toString(),
                email = result.child("email")?.value.toString(),
                phoneNumber = result.child("phoneNumber")?.value.toString(),
                image = result.child("image")?.value.toString(),
                dateCreate = result.child("dateCreate")?.value.toString(),
                dateUpdate = result.child("dateUpdate")?.value.toString()
            )
        )
    }
}

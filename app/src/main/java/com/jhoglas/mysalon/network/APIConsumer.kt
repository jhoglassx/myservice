package com.jhoglas.mysalon.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface APIConsumer {
    @GET("establishment")
    suspend fun getEstablishment(
        @Header("token") token: String,
        @Query("establishmentId") salonId: String,
    ): Response<String>
}

package com.jhoglas.mysalon.network

import okhttp3.Authenticator
import okhttp3.Interceptor

data class ServiceParameters(
    val baseUrl: String,
    val interceptors: Set<Interceptor> = emptySet(),
    val headers: HashMap<String, String> = HashMap(),
    val authenticator: Authenticator? = null,
    val connectTimeout: Long = 15,
    val readTimeout: Long = 15,
    val writeTimeout: Long = 15
)

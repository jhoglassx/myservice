package com.jhoglas.mysalon.data.remote

import com.jhoglas.mysalon.network.APIConsumer
import javax.inject.Inject

class remoteDataSourceImpl @Inject constructor(
    private val api: APIConsumer,
)

package com.jhoglas.mysalon.di

import com.jhoglas.mysalon.data.remote.AuthDataSource
import com.jhoglas.mysalon.data.remote.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface AuthDataSourceModule {
    @Binds
    fun bindAuthUseCase(dataSource: AuthDataSourceImpl): AuthDataSource
}

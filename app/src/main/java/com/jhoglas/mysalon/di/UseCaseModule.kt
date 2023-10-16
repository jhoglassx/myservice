package com.jhoglas.mysalon.di

import com.jhoglas.mysalon.domain.usecase.AuthClientUseCase
import com.jhoglas.mysalon.domain.usecase.AuthClientUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindAuthUseCase(useCase: AuthClientUseCaseImpl): AuthClientUseCase
}

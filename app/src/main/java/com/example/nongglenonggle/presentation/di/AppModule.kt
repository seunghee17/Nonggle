package com.example.nongglenonggle.presentation.di

import com.example.nongglenonggle.domain.repository.AddressRepository
import com.example.nongglenonggle.domain.usecase.UpdateAddressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAddressRepository(): AddressRepository = AddressRepository()

    @Provides
    @Singleton
    fun provideUpdateAddressUseCase(repository: AddressRepository): UpdateAddressUseCase = UpdateAddressUseCase(repository)
}

package com.capstone.nongglenonggle.presentation.di

import com.capstone.nongglenonggle.data.ApplyRepositoryImpl
import com.capstone.nongglenonggle.data.FirestoreGetRepositoryImpl
import com.capstone.nongglenonggle.data.FirestoreSetRepositoryImpl
import com.capstone.nongglenonggle.data.ImageRepositoryImpl
import com.capstone.nongglenonggle.domain.repository.AddressRepository
import com.capstone.nongglenonggle.domain.repository.ApplyRepository
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import com.capstone.nongglenonggle.domain.repository.FirestoreSetRepository
import com.capstone.nongglenonggle.domain.repository.ImageRepository
import com.capstone.nongglenonggle.domain.usecase.UpdateAddressUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        firebaseStorage: FirebaseStorage,
        firebaseAuth: FirebaseAuth
    ):ImageRepository{
        return ImageRepositoryImpl(firebaseStorage,firebaseAuth)
    }
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideSetRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ):FirestoreSetRepository{
        return FirestoreSetRepositoryImpl(firestore,firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideGetRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ):FirestoreGetRepository{
        return FirestoreGetRepositoryImpl(firestore,firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideApplyRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ):ApplyRepository{
        return ApplyRepositoryImpl(firestore,firebaseAuth)
    }



}

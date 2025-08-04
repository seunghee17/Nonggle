package com.capstone.nongglenonggle.app.di

import com.capstone.nongglenonggle.data.ApplyRepositoryImpl
import com.capstone.nongglenonggle.data.AuthRepositoryImpl
import com.capstone.nongglenonggle.data.FirestoreGetRepositoryImpl
import com.capstone.nongglenonggle.data.FirestoreSetRepositoryImpl
import com.capstone.nongglenonggle.data.ImageRepositoryImpl
import com.capstone.nongglenonggle.domain.repository.AddressRepository
import com.capstone.nongglenonggle.domain.repository.ApplyRepository
import com.capstone.nongglenonggle.domain.repository.AuthRepository
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import com.capstone.nongglenonggle.domain.repository.FirestoreSetRepository
import com.capstone.nongglenonggle.domain.repository.ImageRepository
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
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAddressRepository(): AddressRepository = AddressRepository()

    @Provides
    @Singleton
    fun provideImageRepository(
        firebaseStorage: FirebaseStorage,
        firebaseAuth: FirebaseAuth
    ): ImageRepository {
        return ImageRepositoryImpl(firebaseStorage,firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideSetRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): FirestoreSetRepository {
        return FirestoreSetRepositoryImpl(firestore,firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideGetRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): FirestoreGetRepository {
        return FirestoreGetRepositoryImpl(firestore,firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideApplyRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): ApplyRepository {
        return ApplyRepositoryImpl(firestore,firebaseAuth)
    }
}
package com.capstone.nongglenonggle.app.di

import com.capstone.nongglenonggle.data.repositoryimpl.ApplyRepositoryImpl
import com.capstone.nongglenonggle.data.repositoryimpl.AuthenticationRepositoryImpl
import com.capstone.nongglenonggle.data.repositoryimpl.FirestoreGetRepositoryImpl
import com.capstone.nongglenonggle.data.repositoryimpl.FirestoreSetRepositoryImpl
import com.capstone.nongglenonggle.data.repositoryimpl.WorkerResumeRepositoryImpl
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.ApplyRepository
import com.capstone.nongglenonggle.domain.repository.AuthenticationRepository
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import com.capstone.nongglenonggle.domain.repository.FirestoreSetRepository
import com.capstone.nongglenonggle.domain.repository.WorkerResumeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(firestore, firebaseAuth, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideWorkerResumeRepository(
        firebaseStorage: FirebaseStorage,
        firebaseAuth: FirebaseAuth,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): WorkerResumeRepository {
        return WorkerResumeRepositoryImpl(firebaseStorage, firebaseAuth, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideSetRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): FirestoreSetRepository {
        return FirestoreSetRepositoryImpl(firestore, firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideGetRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): FirestoreGetRepository {
        return FirestoreGetRepositoryImpl(firestore, firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideApplyRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): ApplyRepository {
        return ApplyRepositoryImpl(firestore, firebaseAuth)
    }
}
package com.capstone.nongglenonggle.app.di

import android.content.Context
import com.capstone.nongglenonggle.presentation.view.login.GoogleAuthClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideGoogleAuthClient(
        @ApplicationContext context: Context
    ): GoogleAuthClient = GoogleAuthClient(context.applicationContext)
}
package com.capstone.nongglenonggle.domain.repository

import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass
import com.capstone.nongglenonggle.data.AppResult

interface AuthenticationRepository {
    suspend fun setUserData(userData: UserDataClass): AppResult<Unit>
    suspend fun getUserData(): AppResult<UserDataClass>
}
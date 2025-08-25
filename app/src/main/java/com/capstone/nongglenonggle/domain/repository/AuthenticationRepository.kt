package com.capstone.nongglenonggle.domain.repository

import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass

interface AuthenticationRepository {
    suspend fun setUserData(userData: UserDataClass): Result<Unit>
    //suspend fun getUserData(uid: String): Result<Boolean>
}
package com.capstone.nongglenonggle.data.model.login


data class SignInResult (
    val data: UserData?,
    val errorMessage: String?,
    val isNewUser: Boolean?,
)

data class UserData(
    val userId: String,
    val userName: String,
)
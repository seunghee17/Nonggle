package com.capstone.nongglenonggle.data.model.login

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val isNewUser: Boolean? = null,
)
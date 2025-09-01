package com.capstone.nongglenonggle.data.model.sign_up

data class UserDataClass(
    val signUpType: String = "",
    val farmerCategory: List<String> = emptyList(),
    val farmerAddress: String? = null
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "signUpType" to signUpType,
        "farmerCategory" to farmerCategory,
        "farmerAddress" to farmerAddress,
    )
}

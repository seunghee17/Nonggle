package com.example.nongglenonggle.model.login

data class UserInfo(
    val uid:String?,
    val phoneNum:String?,
    val userName:String?

)
{
    constructor():this(null,null,null)
}

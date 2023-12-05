package com.capstone.nongglenonggle.domain.repository

import javax.inject.Inject

class AddressRepository @Inject constructor() {
    private var isData:Boolean = false
    private var addressResult:String = ""

    //데이터 저장
    suspend fun updateAddress(data:String)
    {
        isData = true
        addressResult= data
    }
    fun getAddress():String{
        return addressResult
    }
    fun hasData():Boolean{
        return isData
    }
}
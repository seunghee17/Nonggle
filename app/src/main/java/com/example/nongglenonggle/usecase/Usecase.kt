package com.example.nongglenonggle.usecase

import com.example.nongglenonggle.repository.AddressRepository
import javax.inject.Inject

abstract class Usecase<in Params, out Result> {
    abstract suspend fun execute(data: Params):Result
}

class UpdateAddressUseCase @Inject constructor(
    private val repository: AddressRepository
): Usecase<String, Unit>()
{
    override suspend fun execute(data: String) {
        repository.updateAddress(data)
    }

    fun getAddress():String{
        return repository.getAddress()
    }
    fun hasData():Boolean{
        return repository.hasData()
    }
}
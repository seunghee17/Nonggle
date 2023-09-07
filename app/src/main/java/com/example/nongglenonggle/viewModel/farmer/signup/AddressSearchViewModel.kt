package com.example.nongglenonggle.viewModel.farmer.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddressSearchViewModel : ViewModel() {
    //화면전환을 위한것
    val _navigateToframentD = MutableLiveData<Boolean>()
    val navigateToframentD : LiveData<Boolean>
        get() = _navigateToframentD

    //데이터 유무 상태
    val _isdata = MutableLiveData<Boolean>()
    val isdata:LiveData<Boolean>
        get() = _isdata
    //초기 데이터 상태는 없음으로 세팅
//    init {
//        _isdata.postValue(false)
//    }
    val _addressData = MutableLiveData<String>()
    val addressData : LiveData<String>
        get() = _addressData
    //addressdata전달 함
    fun updateAddressData(data:String)
    {
        _addressData.postValue(data)
    }
    fun updateisData()
    {
        _isdata.postValue(true)
    }
    fun onMoveTo()
    {
        _navigateToframentD.postValue(true)
    }
    fun DoneNavigating()
    {
        _navigateToframentD.postValue(false)
    }
}
package com.example.nongglenonggle.viewModel.farmer.signup

import android.content.SharedPreferences.Editor
import android.text.Editable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nongglenonggle.view.farmer.signup.AddressSearchFragment

class SignupDViewModel: ViewModel() {
    //화면전환을 위한것
    val _navigateToframentaddress = MutableLiveData<Boolean>()
    val navigateToframentaddress : LiveData<Boolean>
        get() = _navigateToframentaddress


    val _isSelect = MutableLiveData<Boolean>()
    val isSelect:LiveData<Boolean>
        get()=_isSelect


    //도로명 주소 받아온 것 저장
    val _addressResult = MutableLiveData<String>()
    val addressResult:LiveData<String>
        get()=_addressResult

    //도로명 주소 textview visible
    val _textviewVisible = MutableLiveData<Boolean>()
    val textviewVisible:LiveData<Boolean>
        get() = _textviewVisible

    //초기값 invisible로 세팅
    init{
        _textviewVisible.postValue(false)
    }
    //textview 변화용
    fun updateisData(){
        _textviewVisible.value = true
    }
    fun updateData(newData:String)
    {
        _addressResult.value= newData
    }

    fun onMoveTo()
    {
        _navigateToframentaddress.value = true
    }
    fun DoneNavigating()
    {
        _navigateToframentaddress.value = false
    }
}
package com.example.nongglenonggle.viewModel.farmer.signup

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.R
import com.example.nongglenonggle.usecase.UpdateAddressUseCase
import com.example.nongglenonggle.view.farmer.signup.AddressSearchFragment
import com.example.nongglenonggle.view.farmer.signup.SignupAFragment
import com.example.nongglenonggle.view.farmer.signup.SignupBFragment
import com.example.nongglenonggle.view.farmer.signup.SignupCFragment
import com.example.nongglenonggle.view.farmer.signup.SignupDFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val updateAddressUseCase: UpdateAddressUseCase) :ViewModel() {

    //현재 fragment 읽어오기
    val currentFragment= MutableLiveData<Fragment>()

    //도로명 주소 변수 정의
    val _addressResult = MutableLiveData<String>()
    val addressResult:LiveData<String>
        get() = _addressResult
    //textview visible용
    val _isdata = MutableLiveData<Boolean>()
    val isdata:LiveData<Boolean>
        get() = _isdata

    fun updateAddress(data:String)
    {
//        viewModelScope.launch {
//            _isdata.postValue(true)
//            _addressResult.postValue(data)
//        }
        viewModelScope.launch {
            updateAddressUseCase.execute(data)
            _addressResult.value = updateAddressUseCase.getAddress()
            _isdata.value = updateAddressUseCase.hasData()
        }
    }


   //보여지는 첫화면 세팅
    init{
       currentFragment.value = SignupDFragment()
       _isdata.postValue(false)
    }

    fun navigateTo(fragment: Fragment){
        currentFragment.value = fragment
    }

    fun navigateToAddressFragment(){
        currentFragment.value = AddressSearchFragment()
    }
    fun navigateToDFragment()
    {
        currentFragment.value = SignupDFragment()
    }


}
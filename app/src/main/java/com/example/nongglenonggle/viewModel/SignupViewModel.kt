package com.example.nongglenonggle.viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nongglenonggle.R
import com.example.nongglenonggle.view.SignupAFragment
import com.example.nongglenonggle.view.SignupBFragment
import com.example.nongglenonggle.view.SignupCFragment
import com.example.nongglenonggle.view.SignupDFragment

class SignupViewModel :ViewModel() {

    //현재 fragment 읽어오기
    val currentFragment= MutableLiveData<Fragment>()

   //보여지는 첫화면 세팅
    init{
       currentFragment.value = SignupAFragment()
    }

    fun navigateTo(fragment: Fragment){
        currentFragment.value = fragment
    }
}
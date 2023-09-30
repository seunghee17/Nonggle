package com.example.nongglenonggle.viewModel.farmer

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class FarmerNoticeViewModel: ViewModel() {
    //이름칸 감지
    val _isClick1 = MutableLiveData<Boolean>()
    val isClick1:LiveData<Boolean> = _isClick1

    //폰번호 입력 감지
    val _isClick2 = MutableLiveData<Boolean>()
    val isClick2:LiveData<Boolean> = _isClick2

    //도로명 주소 받아오기
    val _AddressFromWeb = MutableLiveData<String?>()
    val AddressFromWeb:LiveData<String?> = _AddressFromWeb

    //받아온 도로명 주소 표시
    val _isAddressVisible = MutableLiveData<Boolean>()
    val isAddressVisible:LiveData<Boolean> = _isAddressVisible

    //상세주소 focus용
    val _isClick3 = MutableLiveData<Boolean>()
    val isClick3:LiveData<Boolean> = _isClick3

    //다음버튼 활성화(모든 fragment 공통)
    private val _toNext = MutableLiveData<Boolean>()
    val toNext:LiveData<Boolean> = _toNext

    //다음화면으로 이동위헤
    private val _setFragment = MutableLiveData<Boolean>()
    val setFragment:LiveData<Boolean> = _setFragment


    //fragmentB에서 이렇게 해보겠음
    val _fragmentBState = MutableLiveData<Boolean>()
    val fragmentBState:LiveData<Boolean> = _fragmentBState

    //worker_time1용
    private val _workerTime1 = MutableLiveData<Boolean>()
    val workerTime1:LiveData<Boolean> = _workerTime1

    private val _workerTime2 = MutableLiveData<Boolean>()
    val workerTime2:LiveData<Boolean> = _workerTime2

    //b단계에서 버튼 카테고리 선택시
    private val _category = MutableLiveData<Boolean>()
    val category:LiveData<Boolean> = _category

    //시작날짜 저장용
    var DateList : MutableList<Int> = mutableListOf()

    fun loadAddressData(context:Context){
        val sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val addressData = sharedPreferences.getString("addressData",null)
        _AddressFromWeb.value=addressData
        Log.e("love","${AddressFromWeb.value}")
    }




    fun setActiveButton()
    {
        _category.value = !(_category.value ?: false)
    }

    fun setActiveTime1Button()
    {
        _workerTime1.value = !(_workerTime1.value ?: false)
    }
    fun setActiveTime2Button()
    {
        _workerTime2.value = !(_workerTime1.value ?: false)
    }




    init{
        _isClick1.postValue(false)
        _isClick2.postValue(false)
        _isClick3.postValue(false)
        _toNext.value = false
        _setFragment.value = false
        _fragmentBState.value = false
        _workerTime1.value = false
        _workerTime2.value = false
        _category.value = false
    }





    fun updateNext()
    {
        _toNext.value = !(toNext.value ?: false)
    }
}
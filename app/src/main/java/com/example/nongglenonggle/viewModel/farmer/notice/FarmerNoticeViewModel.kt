package com.example.nongglenonggle.viewModel.farmer.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class FarmerNoticeViewModel: ViewModel() {
    private val _isClick1 = MutableLiveData<Boolean>()
    val isClick1:LiveData<Boolean> = _isClick1

    private val _isClick2 = MutableLiveData<Boolean>()
    val isClick2:LiveData<Boolean> = _isClick2

    private val _isClick3 = MutableLiveData<Boolean>()
    val isClick3:LiveData<Boolean> = _isClick3

    //다음버튼 활성화(모든 fragment 공통)
    private val _toNext = MutableLiveData<Boolean>()
    val toNext:LiveData<Boolean> = _toNext

    //다음화면으로 이동위헤
    private val _setFragment = MutableLiveData<Boolean>()
    val setFragment:LiveData<Boolean> = _setFragment

    //다음 탭 인지를 위한 변수
    val switchTab = MutableLiveData<Boolean>()

    //fragmentB에서 이렇게 해보겠음
    private val _fragmentBState = MutableLiveData<Boolean>()
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

    fun changeToActive()
    {
        _fragmentBState.value = !(_fragmentBState.value ?: false)
    }

    fun setFirst()
    {
        _fragmentBState.value = false
    }

    fun changeToActive1()
    {//이름박스
        _isClick1.value = !(isClick1.value ?: false)
    }
    fun changeToActive2()
    {//연락처
        _isClick2.value = !(isClick2.value ?: false)
    }
    fun changeToActive3()
    {//근무지대표주소2
        _isClick3.value = !(isClick3.value ?: false)
    }

    fun updateNext()
    {
        _toNext.value = !(toNext.value ?: false)
    }
}
package com.example.nongglenonggle.presentation.viewModel.farmer

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.usecase.UploadImageUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmerNoticeViewModel @Inject constructor(
    private val uploadImageUsecase: UploadImageUsecase
): ViewModel() {
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

    //작업 기간 선정용 리스트
    val _DateList = MutableLiveData<List<Int>>()
    val DateList : LiveData<List<Int>> = _DateList

    //상세 작업 내용 저장용
    val _detailContent = MutableLiveData<String>()
    val detailContent:LiveData<String> = _detailContent

    //사진 url저장용
    val _farmerImage = MutableLiveData<String>()
    val farmerImage : LiveData<String> = _farmerImage

    fun uploadImage(imageEntity: Model.ImageEntity){
        viewModelScope.launch {
            val result = uploadImageUsecase.uploadImage(imageEntity)
            if(result.isSuccess){
                //여기서 url접근해서 가져오기
                val imageurl = result.getOrNull()
                _farmerImage.value = imageurl!!
                Log.e("cap", "${farmerImage.value}")
            }
            else{
                Log.e("imageerror", "저장에")
            }
        }
    }


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
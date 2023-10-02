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

    //필요인원 활성화용
    val _requiredPeople =MutableLiveData<Boolean>()
    val requiredPeople:LiveData<Boolean> = _requiredPeople

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

    //성별 선택용

    //여자일경우
    val _activeWomen = MutableLiveData<Boolean>()
    val activeWomen:LiveData<Boolean> = _activeWomen

    //남자일경우
    val _activemen = MutableLiveData<Boolean>()
    val activemen:LiveData<Boolean> = _activemen

    //무관일경우
    val _activeBoth = MutableLiveData<Boolean>()
    val activeBoth:LiveData<Boolean> = _activeBoth

    //성별 데이터 저장용
    val _noticeGender = MutableLiveData<String>()
    val noticeGender:LiveData<String> = _noticeGender

    //일급 active
    val _activedayMoney = MutableLiveData<Boolean>()
    val activedayMoney:LiveData<Boolean> = _activedayMoney

    //시급 active
    val _activeTimeMoney = MutableLiveData<Boolean>()
    val activeTimeMoney:LiveData<Boolean> = _activeTimeMoney

    //급여 협의
    val _activeCounsel = MutableLiveData<Boolean>()
    val activeCounsel:LiveData<Boolean> = _activeCounsel

    //급여정보
    val _noticeMoney = MutableLiveData<String>()
    val noticeMoney : LiveData<String> = _noticeMoney

    //급여 edit용
    val _activeEdittxt = MutableLiveData<Boolean>()
    val activeEdittxt : LiveData<Boolean> = _activeEdittxt

    //고용형태1
    val _activeWorkType1 = MutableLiveData<Boolean>()
    val activeWorkType1 : LiveData<Boolean> = _activeWorkType1

    //고용형태2
    val _activeWorkType2 = MutableLiveData<Boolean>()
    val activeWorkType2 : LiveData<Boolean> = _activeWorkType2

    //고용형태3
    val _activeWorkType3 = MutableLiveData<Boolean>()
    val activeWorkType3 : LiveData<Boolean> = _activeWorkType3

    //고용형태 데이터베이스 저장용
    val _workType= MutableLiveData<String>()
    val workType:LiveData<String> = _workType

    //지원가능경력 활성화용
    val _applyType1 = MutableLiveData<Boolean>()
    val applyType1:LiveData<Boolean> = _applyType1

    val _applyType2 = MutableLiveData<Boolean>()
    val applyType2:LiveData<Boolean> = _applyType2

    val _applyType3 = MutableLiveData<Boolean>()
    val applyType3:LiveData<Boolean> = _applyType3

    //지원가능한경력 데이터베이스 저장용
    val _applyType = MutableLiveData<String>()
    val applyType:LiveData<String> = _applyType

    //자격증 활성화용
    val _activeCertifi1 = MutableLiveData<Boolean>()
    val activeCertifi1:LiveData<Boolean> = _activeCertifi1

    val _activeCertifi2 = MutableLiveData<Boolean>()
    val activeCertifi2:LiveData<Boolean> = _activeCertifi2

    //자격증 필요한경우
    val _active_Edittxt = MutableLiveData<Boolean>()
    val active_Edittxt:LiveData<Boolean> = _active_Edittxt

    //자격증 작성 확인용
    val _active_confirm = MutableLiveData<Boolean>()
    val active_confirm:LiveData<Boolean> = _active_confirm

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
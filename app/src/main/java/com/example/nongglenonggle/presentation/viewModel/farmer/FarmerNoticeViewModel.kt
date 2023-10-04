package com.example.nongglenonggle.presentation.viewModel.farmer

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
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
class FarmerNoticeViewModel @Inject constructor(private val uploadImageUsecase: UploadImageUsecase): ViewModel() {
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


    //다음화면으로 이동위헤
//    private val _setFragment = MutableLiveData<Boolean>()
//    val setFragment:LiveData<Boolean> = _setFragment


    //fragmentB에서 이렇게 해보겠음
    val _fragmentBState = MutableLiveData<Boolean>()
    val fragmentBState:LiveData<Boolean> = _fragmentBState

    //필요인원 활성화용
    val _requiredPeople =MutableLiveData<Boolean>()
    val requiredPeople:LiveData<Boolean> = _requiredPeople

    //spinner 근무요일용
    val _activeWorkDay = MutableLiveData<Boolean>()
    val activeWorkDay:LiveData<Boolean> = _activeWorkDay

    //요일 선택후 edittext visible용
    val _daytextVisible = MutableLiveData<Boolean>()
    val daytextVisible:LiveData<Boolean> = _daytextVisible

    //요일 작성 edittext active용
    val _dayTextActive = MutableLiveData<Boolean>()
    val dayTextActive : LiveData<Boolean> = _dayTextActive

    //spinner 시작 연령 활성화용
    val _activeStartAge=MutableLiveData<Boolean>()
    val activeStartAge:LiveData<Boolean> = _activeStartAge

    //spinner 끝 연령 활성화용
    val _activeEndAge=MutableLiveData<Boolean>()
    val activeEndAge:LiveData<Boolean> = _activeEndAge

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

    //근무시간 선정용 리스트
    val _TimeList = MutableLiveData<List<String>>()
    val TimeList:LiveData<List<String>> = _TimeList

    //시작시간 데이터 들어옴
    val _haveStartData = MutableLiveData<Boolean>()
    val haveStartData:LiveData<Boolean> = _haveStartData

    //시작시간 데이터 들어옴
    val _haveEndData = MutableLiveData<Boolean>()
    val haveEndData:LiveData<Boolean> = _haveEndData

    //datepicker1Txt색상 변화
    val _datepickerTextA= MutableLiveData<Boolean>()
    val datepickerTextA:LiveData<Boolean> = _datepickerTextA

    //datepicker2Txt색상 변화
    val _datepickerTextB= MutableLiveData<Boolean>()
    val datepickerTextB:LiveData<Boolean> = _datepickerTextB

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

    //급여정보 타입
    val _noticeMoney = MutableLiveData<String>()
    val noticeMoney : LiveData<String> = _noticeMoney

    //급여 실제 액수 입력 데이터
    val _payMoney = MutableLiveData<String>()
    val payMoney : LiveData<String> = _payMoney

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

    //숙소제공
    val _activeDorm = MutableLiveData<Boolean>()
    val activeDorm:LiveData<Boolean> = _activeDorm

    val _activeNoDorm = MutableLiveData<Boolean>()
    val activeNoDorm:LiveData<Boolean> = _activeNoDorm

    //숙소제공 데이터 저장
    val _DormType = MutableLiveData<String>()
    val DormType : LiveData<String> = _DormType

    //숙소 정보 active용
    val _yesDormActive=MutableLiveData<Boolean>()
    val yesDormActive:LiveData<Boolean> = _yesDormActive

    //숙소 정보 저장용
    val _yesDormInfo = MutableLiveData<String>()
    val yesDormInfo:LiveData<String> = _yesDormInfo

    //식사정보 active용
    val _yesFood = MutableLiveData<Boolean>()
    val yesFood:LiveData<Boolean> = _yesFood

    val _noFood = MutableLiveData<Boolean>()
    val noFood:LiveData<Boolean> = _noFood

    //식사 제공여부 텍스트
    val _FoodType = MutableLiveData<String>()
    val FoodType:LiveData<String> = _FoodType

    //식사제공 부가정보 active
    val _yesFoodActive=MutableLiveData<Boolean>()
    val yesFoodActive:LiveData<Boolean> = _yesFoodActive

    //식사제공일 경우 부가정보 데베용
    val _yesFoodInfo = MutableLiveData<String>()
    val yesFoodInfo:LiveData<String> = _yesFoodInfo

    //공고글 마감기한 active
    val _yesDeadlineActive = MutableLiveData<Boolean>()
    val yesDeadlineActive:LiveData<Boolean> = _yesDeadlineActive

    val _noDeadlineActive = MutableLiveData<Boolean>()
    val noDeadlineActive:LiveData<Boolean> = _noDeadlineActive

    //공고글 마감기한 타입용
    val _DeadlineType = MutableLiveData<String>()
    val DeadlineType : LiveData<String> = _DeadlineType

    //작업 마감기한 값 들어왔다면
    val _isDeadline = MutableLiveData<Boolean>()
    val isDeadline:LiveData<Boolean> = _isDeadline


    fun uploadImage(imageEntity: Model.ImageEntity){
        viewModelScope.launch {
            val result = uploadImageUsecase.uploadImage(imageEntity)
            if(result.isSuccess){
                //여기서 url접근해서 가져오기
                val imageurl = result.getOrNull()
                _farmerImage.value = imageurl!!
            }
            else{
               Log.e("error", "이미지 업로드 실패")
            }
        }
    }


    fun loadAddressData(context:Context){
        val sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val addressData = sharedPreferences.getString("addressData",null)
        _AddressFromWeb.value=addressData
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
        //_setFragment.value = false
        _fragmentBState.value = false
        _workerTime1.value = false
        _workerTime2.value = false
        _category.value = false
    }





}
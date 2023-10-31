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
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.usecase.AddNoticeRefToUserUseCase
import com.example.nongglenonggle.domain.usecase.AddNoticeToCategoryUseCase
import com.example.nongglenonggle.domain.usecase.AddNoticeToGenderUseCase
import com.example.nongglenonggle.domain.usecase.AddNoticeToTypeUseCase
import com.example.nongglenonggle.domain.usecase.AddNoticeUseCase
import com.example.nongglenonggle.domain.usecase.AddRefToAddressUseCase
import com.example.nongglenonggle.domain.usecase.UploadImageUsecase
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmerNoticeViewModel @Inject constructor(
    private val uploadImageUsecase: UploadImageUsecase,
    private val addNoticeUseCase: AddNoticeUseCase,
    private val addNoticeRefToUserUseCase: AddNoticeRefToUserUseCase,
    private val addRefToAddressUseCase: AddRefToAddressUseCase,
    private val addNoticeToCategoryUseCase: AddNoticeToCategoryUseCase,
    private val addNoticeToGenderUseCase: AddNoticeToGenderUseCase,
    private val addNoticeToTypeUseCase: AddNoticeToTypeUseCase
    ): ViewModel() {
    //이름칸 감지
    val _isClick1 = MutableLiveData<Boolean>()
    val isClick1:LiveData<Boolean> = _isClick1

    var name:String = ""
    var phnum:String = ""

    //폰번호 입력 감지
    val _isClick2 = MutableLiveData<Boolean>()
    val isClick2:LiveData<Boolean> = _isClick2

    //도로명 주소 받아오기
    val _AddressFromWeb = MutableLiveData<String?>()
    val AddressFromWeb:LiveData<String?> = _AddressFromWeb

    //시도, 시군구 각각 얻기 위해서
    val locationArray: Array<String> by lazy {
        AddressFromWeb.value?.split(" ")?.toTypedArray() ?: arrayOf()
    }

    val firstElement: String? by lazy {
        locationArray.getOrNull(0)
    }

    val secondElement: String? by lazy {
        locationArray.getOrNull(1)
    }


    var totalAddress:String=""

    //받아온 도로명 주소 표시
    val _isAddressVisible = MutableLiveData<Boolean>()
    val isAddressVisible:LiveData<Boolean> = _isAddressVisible

    //상세주소 focus용
    val _isClick3 = MutableLiveData<Boolean>()
    val isClick3:LiveData<Boolean> = _isClick3


    //fragmentB에서 이렇게 해보겠음
    val _fragmentBState = MutableLiveData<Boolean>()
    val fragmentBState:LiveData<Boolean> = _fragmentBState

    var titleInfo:String=""

    //필요인원 활성화용
    val _requiredPeople =MutableLiveData<Boolean>()
    val requiredPeople:LiveData<Boolean> = _requiredPeople

    //필요인원 데이터 저장
    val _requirednum = MutableLiveData<String>()
    val requirednum:LiveData<String> = _requirednum

    //spinner 근무요일용
    val _activeWorkDay = MutableLiveData<Boolean>()
    val activeWorkDay:LiveData<Boolean> = _activeWorkDay

    //요일 선택후 edittext visible용
    val _daytextVisible = MutableLiveData<Boolean>()
    val daytextVisible:LiveData<Boolean> = _daytextVisible

    //요일 작성 edittext active용
    val _dayTextActive = MutableLiveData<Boolean>()
    val dayTextActive : LiveData<Boolean> = _dayTextActive

    //요일작성 (ex.주 3일)
    val _dayType = MutableLiveData<String>()
    val dayType : LiveData<String> = _dayType

    val _dayDetail = MutableLiveData<String>()
    val dayDetail:LiveData<String> = _dayDetail

    //spinner 시작 연령 활성화용
    val _activeStartAge=MutableLiveData<Boolean>()
    val activeStartAge:LiveData<Boolean> = _activeStartAge

    //spinner 끝 연령 활성화용
    val _activeEndAge=MutableLiveData<Boolean>()
    val activeEndAge:LiveData<Boolean> = _activeEndAge

    //연령대 받는 변수
    var agedata : String = ""

    //spinner 연령대 저장list
    val recruitAge : MutableList<String> = mutableListOf()

    //worker_time1용
    val _workerTime1 = MutableLiveData<Boolean>()
    val workerTime1:LiveData<Boolean> = _workerTime1

    val _workerTime2 = MutableLiveData<Boolean>()
    val workerTime2:LiveData<Boolean> = _workerTime2

    //b단계에서 버튼 카테고리 선택시
    private val _category = MutableLiveData<Boolean>()
    val category:LiveData<Boolean> = _category

    //작업 기간 선정용 리스트
    val _DateList = MutableLiveData<List<Int>>()
    val DateList : LiveData<List<Int>> = _DateList

    //작업기간 데이터베이스에 넣을용
    val _workPeriod = MutableLiveData<List<String>>()
    val workPeriod:LiveData<List<String>> = _workPeriod

    //근무시간 선정용 리스트
    val _TimeList = MutableLiveData<List<String>>()
    val TimeList:LiveData<List<String>> = _TimeList


    //근무시간 데이터 넣을 변수
    var timeDetail : String = ""
    var timeDetailA : String=""
    var result : String=""

    var timeType:String = ""

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


    //자격증 입력 항목 visible용 A
    val _visibleA = MutableLiveData<Boolean>()
    val visibleA : LiveData<Boolean> = _visibleA

    val _visibleB = MutableLiveData<Boolean>()
    val visibleB : LiveData<Boolean> = _visibleB

    val _visibleC = MutableLiveData<Boolean>()
    val visibleC : LiveData<Boolean> = _visibleC

    //자격증 필요여부 저장용
    val _certifiType = MutableLiveData<String>()
    val certifiType:LiveData<String> = _certifiType

    //자격증 입력시 저장 list
    val _certifiList = MutableLiveData<List<String>>()
    val certifiList : LiveData<List<String>> = _certifiList

    fun removeItemAt(index: Int) {
        val currentList = _certifiList.value?.toMutableList()
        if(index>0){
            currentList?.removeAt(index)
            _certifiList.postValue(currentList!!)
        }
        else{
            currentList?.removeAt(index)
            _certifiList.value = emptyList()
        }
    }


    //출력용 변수들
    val certifiA : MutableLiveData<String> = MutableLiveData()
    val certifiB : MutableLiveData<String> = MutableLiveData()
    val certifiC : MutableLiveData<String> = MutableLiveData()

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

    var deadlineDetail : String = ""

    //작업 마감기한 값 들어왔다면
    val _isDeadline = MutableLiveData<Boolean>()
    val isDeadline:LiveData<Boolean> = _isDeadline



    fun uploadImage(imageEntity: Model.ImageEntity){
        viewModelScope.launch {
            val result = uploadImageUsecase.uploadImage(imageEntity,"NoticeImages")
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
    val categoryButtons = MutableLiveData<List<Boolean>>(List(8) {false})
    val categoryLive:LiveData<List<Boolean>> = categoryButtons
    //선택한 버튼 텍스트 저장용
    val clickedTexts = mutableListOf<String>()

    fun onButtonClick(index:Int, buttonText:String){
        val currentColor = categoryButtons.value?.toMutableList() ?: mutableListOf()
        currentColor[index] = !currentColor[index]
        categoryButtons.value = currentColor
        if(currentColor[index]){
            clickedTexts.add(buttonText)
        }else{
            clickedTexts.remove(buttonText)
        }
    }

    //기타품목 포커스용
    private val _focusAdditional = MutableLiveData<Boolean>()
    val focusAdditional : LiveData<Boolean> = _focusAdditional

    //기타품목 입력
    var additionalTxt : String = ""
    fun setAdditional(focus:Boolean){
        _focusAdditional.postValue(focus)
    }


    init{
        _isClick1.postValue(false)
        _isClick2.postValue(false)
        _isClick3.postValue(false)
        _fragmentBState.value = false
        _workerTime1.value = false
        _workerTime2.value = false
        _category.value = false
        _daytextVisible.postValue(false)
    }
    fun setNoticeData():NoticeContent{
        val allNoticeContent = NoticeContent(
            imageUrl = farmerImage.value.toString(),
            pay = listOf(noticeMoney.value ?: "",payMoney.value ?: ""),
            recruitPeriod= mapOf(
                "type" to DeadlineType.value!!,
                "detail" to deadlineDetail
            ),
            recruitNum=requirednum.value!!,
            recruitAge = recruitAge,
            recruitGender = noticeGender.value!!,
            career = applyType.value!!,
            qualification = mapOf(
                "type" to certifiType.value!!,
                "name" to certifiList
            ),
            workPeriod = workPeriod.value!!,
            workType = workType.value!!,
            workDay = mapOf(
                "type" to dayType.value!!,
                "detail" to dayDetail.value!!
            ),
            workTime = mapOf(
                "type" to timeType,
                "detail" to result
            ),
            title = titleInfo,
            categoryItem = clickedTexts,
            firstAddress = AddressFromWeb.value!!,
            finalAddress = totalAddress,
            workDetailInfo = detailContent.value!!,
            stayInfo = DormType.value!!,
            mealInfo = FoodType.value!!,
            specialInfo = mapOf(
                "name" to name,
                "phnum" to phnum
            )
        )
        return allNoticeContent
    }
    fun addNoticeContent(noticeContent: NoticeContent){
        viewModelScope.launch {
            try{
                addNoticeUseCase.invoke(noticeContent)
                val docRef = addNoticeUseCase.invoke(noticeContent)
                //이제 이를 활용해 필터링할 항목 문서들에 저장할 수 있다
                //val docPath = docRef.path
                addNoticeRefToUser(docRef)
                addRefToAddress(docRef,"Announcement",firstElement ?: "none",secondElement ?: "none")
                val categorytext = clickedTexts[0]
                addNoticeRefToCategory(docRef, categorytext)
                addNoticeRefToGender(docRef,noticeGender.value.toString())
                addNoticeToType(docRef,workType.value.toString())
            }catch (e:Exception){
                Log.d("first", "fail to database")
            }
        }
    }

    //user 개인 테이블에 ref저장
    fun addNoticeRefToUser(docRef: DocumentReference){
        viewModelScope.launch {
            try{
                addNoticeRefToUserUseCase.invoke(docRef)
            }catch (e:Exception){
                Log.d("second", "fail to database")
            }
        }
    }
    //주소에 따른 저장
    fun addRefToAddress(docRef: DocumentReference, type:String, id1:String,id2:String){
        viewModelScope.launch {
            try{
                addRefToAddressUseCase.invoke(docRef,type,id1, id2)
            }catch (e:Exception){
                Log.d("third", "fail to address")
            }
        }
    }

    //카테고리에 따른 저장
    fun addNoticeRefToCategory(docRef: DocumentReference, id:String){
        viewModelScope.launch{
            try {
                addNoticeToCategoryUseCase.invoke(docRef, id)
            }catch (e:Exception){
                Log.d("four", "fail to category")
            }
        }
    }
    //성별에 따른 저장
    fun addNoticeRefToGender(docRef: DocumentReference, id:String){
        viewModelScope.launch{
            try {
                addNoticeToGenderUseCase.invoke(docRef, id)
            }catch (e:Exception){
                Log.d("five", "fail to gender")
            }
        }
    }

    //근무 유형별 저장
    fun addNoticeToType(docRef: DocumentReference, id:String){
        viewModelScope.launch{
            try {
                addNoticeToTypeUseCase.invoke(docRef, id)
            }catch (e:Exception){
                Log.d("six", "fail to type")
            }
        }
    }

}
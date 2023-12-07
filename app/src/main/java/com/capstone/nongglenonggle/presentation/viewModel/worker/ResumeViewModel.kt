package com.capstone.nongglenonggle.presentation.viewModel.worker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.domain.entity.Model
import com.capstone.nongglenonggle.domain.entity.ResumeContent
import com.capstone.nongglenonggle.domain.entity.ResumeSummary
import com.capstone.nongglenonggle.domain.usecase.AddByAgeUseCase
import com.capstone.nongglenonggle.domain.usecase.AddCategoryUseCase
import com.capstone.nongglenonggle.domain.usecase.AddGenderUseCase
import com.capstone.nongglenonggle.domain.usecase.AddRefToAddressUseCase
import com.capstone.nongglenonggle.domain.usecase.AddResumeRefToUserUseCase
import com.capstone.nongglenonggle.domain.usecase.AddResumeUseCase
import com.capstone.nongglenonggle.domain.usecase.AddTypeUseCase
import com.capstone.nongglenonggle.domain.usecase.UploadImageUsecase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.util.Calendar
import javax.inject.Inject
@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val uploadImageUsecase: UploadImageUsecase,
    private val addResumeUseCase: AddResumeUseCase,
    private val addResumeRefToUserUseCase: AddResumeRefToUserUseCase,
    private val addRefToAddressUseCase: AddRefToAddressUseCase,
    private val addResumeByAgeUseCase: AddByAgeUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val addGenderUseCase: AddGenderUseCase,
    private val addTypeUseCase: AddTypeUseCase
    ): ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _profileImage = MutableLiveData<String>()
    val profileImage:LiveData<String> = _profileImage

    val resumeData : MutableLiveData<MutableList<ResumeSummary>> = MutableLiveData(mutableListOf())
    var allcareer : String=""
    var additional_present:String=""
    var openSetting1:String=""
    var openSetting2:String=""


    //이름
    private val _changeFocus = MutableLiveData<Boolean>()
    val changeFocus:LiveData<Boolean> = _changeFocus

    //total
    var totalPeriod:String = ""
    var periodOfWorking:String=""

    var userName:String=""

    fun setFocus(isfocus:Boolean){
        _changeFocus.postValue(isfocus)
    }

    //생년월일
    val _BirthList = MutableLiveData<List<Int>>()
    val BirthList : LiveData<List<Int>> = _BirthList

    //생년월일 line활성화
    private val _BirthLine = MutableLiveData<Boolean>()
    val BirthLine:LiveData<Boolean> = _BirthLine

    fun setActive(){
        _BirthLine.value = _BirthLine.value!!.not()
    }

    //currentYear- BirthList.value?.get(0)
    var userYear = 0
    fun calculate(user:Int){
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        userYear= currentYear - user
    }



    fun uploadImage(imageEntity: Model.ImageEntity){
        viewModelScope.launch {
            val result = uploadImageUsecase.uploadImage(imageEntity,"ResumeImages")
            if(result.isSuccess){
                //여기서 url접근해서 가져오기
                val imageurl = result.getOrNull()
                if(imageurl != null){
                    _profileImage.value = imageurl!!
                }else{
                }
            }
            else{
            }
        }
    }
    //성별
    private val _activateWomen = MutableLiveData<Boolean>()
    val activateWomen:LiveData<Boolean> = _activateWomen
    private val _activateMen = MutableLiveData<Boolean>()
    val activateMen:LiveData<Boolean> = _activateMen
    var gender = ""
    fun activeWomen(){
        _activateWomen.postValue(true)
        _activateMen.postValue(false)
        gender = "여"
    }
    fun activeMan(){
        _activateWomen.postValue(false)
        _activateMen.postValue(true)
        gender = "남"
    }
    //자격증
    private val _activeCertifiYes = MutableLiveData<Boolean>()
    val activeCertifiYes:LiveData<Boolean> = _activeCertifiYes
    private val _activeCertifiNo = MutableLiveData<Boolean>()
    val activeCertifiNo:LiveData<Boolean> = _activeCertifiNo
    fun activeCertifiA(){
        _activeCertifiYes.postValue(true)
        _activeCertifiNo.postValue(false)
    }
    fun activeCertifiB(){
        _activeCertifiYes.postValue(false)
        _activeCertifiNo.postValue(true)
    }
    //자격증 입력
    private val _changeFocusCertifi = MutableLiveData<Boolean>()
    val changeFocusCertifi:LiveData<Boolean> = _changeFocusCertifi
    fun setFocusCertifi(isfocus:Boolean){
        _changeFocusCertifi.postValue(isfocus)
    }
    //확인버튼 활성화용
    val _changeConfirmCertifi = MutableLiveData<Boolean>()
    val changeConfirmCertifi : LiveData<Boolean> = _changeConfirmCertifi

    //자격증 입력받는 리스트
    private var _CarrerList=MutableLiveData<MutableList<String>>()
    val CarrerList:LiveData<MutableList<String>> = _CarrerList

    fun storeCarrer(text:String){
        val currentList = _CarrerList.value ?: mutableListOf()
        currentList.add(text)
        _CarrerList.value = currentList
    }
    fun removeCarrer(index:Int){
        val currentList = _CarrerList.value ?: mutableListOf()
        currentList.removeAt(index)
        _CarrerList.value = currentList
    }

    private val _activeMonthA = MutableLiveData<Boolean>()
    val activeMonthA : LiveData<Boolean> = _activeMonthA
    private val _activeMonthB = MutableLiveData<Boolean>()
    val activeMonthB : LiveData<Boolean> = _activeMonthB
    fun activeA(){
        _activeMonthA.postValue(true)
        _activeMonthB.postValue(false)
    }
    fun activeB(){
        _activeMonthA.postValue(false)
        _activeMonthB.postValue(true)
    }
    fun clearActive(){
        _activeMonthA.postValue(false)
        _activeMonthB.postValue(false)
    }
    private val _activeCareerEdit = MutableLiveData<Boolean>()
    val activeCareerEdit:LiveData<Boolean> = _activeCareerEdit
    //경력사항 active용
    fun activeCareer(focus:Boolean){
        _activeCareerEdit.postValue(focus)
    }
    var careerTitle=""

    var _activeSpinner = MutableLiveData<Boolean>()
    val activeSpinner : LiveData<Boolean> = _activeSpinner

    var getSpinnerValue:String = ""

    var _selectMonthYear=MutableLiveData<List<Int>>()
    val selectMonthYear:LiveData<List<Int>> = _selectMonthYear

    //미만시 선택 년월 활성화용
     var _activeMonthYear = MutableLiveData<Boolean>()
    val activeMonthYear:LiveData<Boolean> = _activeMonthYear

    //이상시 선택 년월 활성화용 기간 전
    var _activeMonthYearA = MutableLiveData<Boolean>()
    val activeMonthYearA:LiveData<Boolean> = _activeMonthYearA

    //이상시 선택 년월 활성화용 기간 후
    var _activeMonthYearB = MutableLiveData<Boolean>()
    val activeMonthYearB:LiveData<Boolean> = _activeMonthYearB

    var careerDetail:String= ""
    //추가버튼
    private val _activePlus = MutableLiveData<Boolean>()
    val activePlus:LiveData<Boolean> = _activePlus

    fun setAddActive(){
        _activePlus.postValue(true)
    }
    init{
        _BirthLine.postValue(false)
        _activeCareerEdit.postValue(false)
        _activePlus.postValue(false)
    }
    //recyclerview에 넣을 함수 세팅
    fun setResumeSummary(){
        //1개월 이상일때
        if(activeMonthB.value == true) {
            //계산하는 함수 호출
            val start = LocalDate.of(selectMonthYear.value!!.get(0), selectMonthYear.value!!.get(1),1)
            val end = LocalDate.of(selectMonthYear.value!!.get(2), selectMonthYear.value!!.get(3),1)
            //몇년 몇개월
            getTotal(start,end)
            periodOfWorking = "${selectMonthYear.value!!.get(0)}.${selectMonthYear.value!!.get(1)} ~ ${selectMonthYear.value!!.get(2)}.${selectMonthYear.value!!.get(3)}"
        }
        else{
            //몇일
            totalPeriod = getSpinnerValue
            periodOfWorking = "${selectMonthYear.value!!.get(0)}.${selectMonthYear.value!!.get(1)}"
        }
        val current = resumeData.value ?: mutableListOf()
        current.add(ResumeSummary(title = careerTitle , date = periodOfWorking, total = totalPeriod, description = careerDetail))
        resumeData.value = current
    }

    fun getCareerTotal():String{
        var year = 0
        var month=0
        var day=0
        resumeData.value?.forEach {
            if(it.total.contains("년")){
                val partyear = it.total.split("년")[0].trim().toInt()
                val partmonth = it.total.split("년")[1].split("개월")[0].trim().toInt()
                year += partyear
                month += partmonth
            }
            else {
                val partday = it.total.replace("일", "").trim().toInt()
                day += partday
            }
        }
        //오버플로우 처리
        month += day/30
        day %=30
        year += month/12
        month %=12
        allcareer = "${year}년 ${month}개월 ${day}일"
        return "${year}년 ${month}개월 ${day}일"
    }

    fun getTotal(startDate:LocalDate, endDate:LocalDate){
        //1개월 이상일때 계산용
        val period = Period.between(startDate,endDate)
        totalPeriod =  "${period.years}년 ${period.months}개월"
    }

    //초기화코드
    fun getClearData(){
        clearActive()
        careerDetail = ""
        _selectMonthYear.value = emptyList()
        getSpinnerValue=""
        careerDetail = ""
        periodOfWorking = ""
        totalPeriod = ""
    }
    private val _activePresent = MutableLiveData<Boolean>()
    val activePresent:LiveData<Boolean> = _activePresent
    fun setActivePresent(focus:Boolean){
        _activePresent.postValue(focus)
    }
    var presentTxt : String=""

    private val _activeCharacter = MutableLiveData<Boolean>()
    val activeCharacter:LiveData<Boolean> = _activeCharacter
    fun setActiveCharacter(focus:Boolean){
        _activeCharacter.postValue(focus)
    }
    var _activeCharacterConfirm = MutableLiveData<Boolean>()
    val activeCharacterConfirm:LiveData<Boolean> = _activeCharacterConfirm

    private val _characterList = MutableLiveData<MutableList<String>>()
    val characterList:LiveData<MutableList<String>> = _characterList
    fun storeCharacter(text:String){
        val current = _characterList.value ?: mutableListOf()
        current.add(text)
        _characterList.value = current
    }
    fun removeCharacter(index:Int){
        val currentList = _characterList.value ?: mutableListOf()
        currentList.removeAt(index)
        _characterList.value = currentList
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
    val _clickDormA = MutableLiveData<Boolean>()
    val clickDormA:LiveData<Boolean> = _clickDormA
    val _clickDormB = MutableLiveData<Boolean>()
    val clickDormB:LiveData<Boolean> = _clickDormB

    //근무형태
    var dormType : String=""

    //요일데이터 주 몇일
    var dayData : String=""
    var dayDetailData : String=""

    val _activedaySpinner = MutableLiveData<Boolean>()
    val activedaySpinner : LiveData<Boolean> = _activedaySpinner

    private val _focusSpinnerEdit = MutableLiveData<Boolean>()
    val focusSpinnerEdit : LiveData<Boolean> = _focusSpinnerEdit

    fun setEditFocus(focus:Boolean){
        _focusSpinnerEdit.postValue(focus)
    }

    val _activeprivate=MutableLiveData<Boolean>()
    val activeprivate:LiveData<Boolean> = _activeprivate
    val _activepublic = MutableLiveData<Boolean>()
    val activepublic : LiveData<Boolean> = _activepublic

    //희망지역 선택
    private val _locationSelect = MutableLiveData<MutableList<String>>()
    val locationSelect:LiveData<MutableList<String>> = _locationSelect

    val firstElement:String? by lazy{
        locationSelect.value?.getOrNull(0)
    }
    val secondElement:String? by lazy{
        locationSelect.value?.getOrNull(1)
    }
    val thirdElement:String? by lazy{
        locationSelect.value?.getOrNull(2)
    }
    val fourthElement:String? by lazy{
        locationSelect.value?.getOrNull(3)
    }
    val fifthElement:String? by lazy{
        locationSelect.value?.getOrNull(4)
    }
    val sixthElement:String? by lazy{
        locationSelect.value?.getOrNull(5)
    }

    fun storeLocation(text:String){
        val current = _locationSelect.value ?: mutableListOf()
        current.add(text)
        _locationSelect.value = current
    }
    fun removeLocation(index:Int){
        var current = _locationSelect.value?.toMutableList() ?: mutableListOf()
        if(index >= 0 && index<current!!.size){
            current.removeAt(index)
        }
        _locationSelect.value = current
    }

    fun setResumeData():ResumeContent{
        val allResumeContent = ResumeContent(
            imageurl = profileImage.value.toString(),
            userName = userName,
            userYear = userYear,
            userGender = gender,
            userPresent = presentTxt,
            allCareer = allcareer,
            resumeData = resumeData.value ?: mutableListOf(),
            careerList = CarrerList.value ?: mutableListOf(),
            locationSelect = locationSelect.value ?: mutableListOf(),
            dormType = dormType,
            dayData = dayData,
            dayDetailData = dayDetailData,
            desiredItem = clickedTexts,
            selfHastag = characterList.value ?: mutableListOf(),
            selfInfo = additional_present,
            uid= firebaseAuth.currentUser?.uid!!
        )
        return allResumeContent
    }

    //전체 저장용
    suspend fun addResumeContent(resumeContent: ResumeContent,opensetting1:String, opensetting2:String){
        viewModelScope.launch {
            try{
                addResumeUseCase.invoke(resumeContent,opensetting1,opensetting2)
                val docRef = addResumeUseCase.invoke(resumeContent,opensetting1,opensetting2)

                addResumeRefToUser(docRef)
                if(locationSelect.value?.size == 2){
                    addRefToAddress(docRef,"ResumeFilter",firstElement!!,secondElement!!)
                }
                if(locationSelect.value?.size == 4){
                    addRefToAddress(docRef,"ResumeFilter",firstElement!!,secondElement!!)
                    addRefToAddress(docRef,"ResumeFilter",thirdElement!!,fourthElement!!)
                }
                if(locationSelect.value?.size == 6){
                    addRefToAddress(docRef,"ResumeFilter",firstElement!!,secondElement!!)
                    addRefToAddress(docRef,"ResumeFilter",thirdElement!!,fourthElement!!)
                    addRefToAddress(docRef,"ResumeFilter",fifthElement!!,sixthElement!!)
                }
                addByAge(docRef,"${(userYear/10)*10}대")
                addRefToCategory("ResumeCategory",docRef,clickedTexts.get(0))
                addRefToCategory("ResumeCategory",docRef,clickedTexts.get(1))
                addRefToCategory("ResumeCategory",docRef,clickedTexts.get(2))
                addNoticeRefToGender("ResumeGender",docRef,gender)
                addNoticeToType("ResumeWorkType",docRef,dormType)
            }catch (e:Exception){

            }
        }
    }
    fun addResumeRefToUser(docRef: DocumentReference){
        viewModelScope.launch {
            try{
                addResumeRefToUserUseCase.invoke(docRef)
            }catch (e:Exception){

                throw e
            }
        }
    }
    fun addRefToAddress(docRef: DocumentReference, type:String, id1:String,id2:String){
            viewModelScope.launch {
                try{
                    addRefToAddressUseCase.invoke(docRef,type,id1, id2)
                }catch (e:Exception){

                }
            }
        }
    fun addByAge(docRef:DocumentReference, id:String){
        viewModelScope.launch {
            try {
                addResumeByAgeUseCase.invoke(docRef,id)
            }catch (e:Exception){

                throw e
            }
        }
    }
    fun addRefToCategory(name:String,docRef: DocumentReference, id:String){
            viewModelScope.launch{
                try {
                    addCategoryUseCase.invoke(name,docRef, id)
                }catch (e:Exception){

                }
            }
        }

    fun addNoticeRefToGender(name:String,docRef: DocumentReference, id:String){
            viewModelScope.launch{
                try {
                    addGenderUseCase.invoke(name,docRef, id)
                }catch (e:Exception){

                }
            }
        }

    //근무 유형별 저장
        fun addNoticeToType(name:String,docRef: DocumentReference, id:String){
            viewModelScope.launch{
                try {
                    addTypeUseCase.invoke(name,docRef, id)
                }catch (e:Exception){

                }
            }
        }

}
package com.example.nongglenonggle.presentation.viewModel.worker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.usecase.UploadImageUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
@HiltViewModel
class ResumeViewModel @Inject constructor(private val uploadImageUsecase: UploadImageUsecase): ViewModel() {
    private val _profileImage = MutableLiveData<String>()
    val profileImage:LiveData<String> = _profileImage

    //이름
    private val _changeFocus = MutableLiveData<Boolean>()
    val changeFocus:LiveData<Boolean> = _changeFocus

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

    init{
        _BirthLine.postValue(false)
    }

    fun uploadImage(imageEntity: Model.ImageEntity){
        viewModelScope.launch {
            val result = uploadImageUsecase.uploadImage(imageEntity,"ResumeImages")
            if(result.isSuccess){
                //여기서 url접근해서 가져오기
                val imageurl = result.getOrNull()
                _profileImage.value = imageurl!!
            }
            else{
                Log.e("error", "이미지 업로드 실패")
            }
        }
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

    //경력사항 입력받는 리스트
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
}
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
import javax.inject.Inject
@HiltViewModel
class ResumeViewModel @Inject constructor(private val uploadImageUsecase: UploadImageUsecase): ViewModel() {
    private val _profileImage = MutableLiveData<String>()
    val profileImage:LiveData<String> = _profileImage

    private val _changeFocus = MutableLiveData<Boolean>()
    val changeFocus:LiveData<Boolean> = _changeFocus

    var userName:String=""

    fun setFocus(){
        _changeFocus.postValue(true)
    }

    fun uploadImage(imageEntity: Model.ImageEntity){
        viewModelScope.launch {
            val result = uploadImageUsecase.uploadImage(imageEntity)
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
}
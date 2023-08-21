package com.example.nongglenonggle.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel() {
    //비밀번호 포커스
    private val _isEditTextFocused = MutableLiveData<Boolean>()
    private val _isIDEditTextFocused = MutableLiveData<Boolean>()
    private val _showdeleteImage = MutableLiveData<Boolean>()

    val showdeleteImage:LiveData<Boolean>
        get() = _showdeleteImage

    val isEditTextFocused: LiveData<Boolean>
        get() = _isEditTextFocused

    val isIDEditTextFocused : LiveData<Boolean>
        get() = _isIDEditTextFocused

    fun setEditTextFocused(hasFocus:Boolean){
        _isEditTextFocused.value=hasFocus
        _showdeleteImage.value=hasFocus
    }

    fun setIDEditTextFocused(hasFocus: Boolean){
        _isIDEditTextFocused.value = hasFocus
        _showdeleteImage.value=hasFocus
    }
}
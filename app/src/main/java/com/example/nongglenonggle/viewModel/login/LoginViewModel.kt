package com.example.nongglenonggle.viewModel.login
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.model.login.LoginModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(private val loginModel: LoginModel) : ViewModel() {
    constructor(): this(LoginModel(FirebaseFirestore.getInstance()))
    private val auth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore
    //비밀번호 포커스
    private val _isEditTextFocused = MutableLiveData<Boolean>()
    private val _isIDEditTextFocused = MutableLiveData<Boolean>()
    private val _showdeleteImage = MutableLiveData<Boolean>()
    //로그인 상태 업데이트를 위해
    var isLoginAvailable=false

    //사용자 이름을 받아오기 위한 변수
    val userName=MutableLiveData<String?>()
    //회원 유형 감지용
    val _isFarmer=MutableLiveData<Boolean>()
    val isFarmer :LiveData<Boolean>
        get() = _isFarmer

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
    fun loginWithEmailAndPassword(email: String, password:String):Boolean
    {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{signInTask ->
                //로그인 성공시
                if(signInTask.isSuccessful)
                {
                    viewModelScope.launch{
                        withContext(Dispatchers.Main){
                            //view에 상태 전달용
                            isLoginAvailable = true
                        }
                    }
                    //로그인 성공시 uid값 가져오기
                    val user = auth.currentUser
                    val uid = user!!.uid
                    fetchFarmerInfo(uid)
                    if(isFarmer.value==true)
                    {
                        fetchFarmerInfo(uid)
                    }
                    else{
                        fetchWorkerInfo(uid)
                    }

                }
                //로그인 실패시
                else{
                    isLoginAvailable=false
                }
            }
        return isLoginAvailable
    }

    fun fetchFarmerInfo(uid:String){
        loginModel.getFarmerUserInfo(uid, onSuccess={
            userInfo ->  userName.value = userInfo.userName
            _isFarmer.value = true
        },
            onFailure = {
                _isFarmer.value=false
            })
    }
    fun fetchWorkerInfo(uid:String)
    {
        loginModel.getWorkerUserInfo(uid, onSuccess = {
            userInfo ->  userName.value = userInfo.userName
        },
            onFailure = {
                _isFarmer.value=false
            })
    }

}
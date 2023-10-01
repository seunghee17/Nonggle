package com.example.nongglenonggle.presentation.viewModel.login
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class LoginViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    //비밀번호 포커스
    private val _isEditTextFocused = MutableLiveData<Boolean>()
    private val _isIDEditTextFocused = MutableLiveData<Boolean>()
    private val _showdeleteImage = MutableLiveData<Boolean>()
    //로그인 상태 업데이트를 위해
    //var isLoginAvailable=false
    private val _isLoginAvailable=MutableLiveData<Boolean>()
    val isLoginAvailable:LiveData<Boolean>
        get() = _isLoginAvailable



    //회원 유형 감지용
    private val _isFarmer=MutableLiveData<Boolean>()
    val isFarmer :LiveData<Boolean> = _isFarmer

    private val _isWorker=MutableLiveData<Boolean>()
    val isWorker:LiveData<Boolean> = _isWorker

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

    init{
        _isLoginAvailable.value = false
        _isFarmer.postValue(false)
    }

    //사용자 유형 감지하기 위한 uid 데이터 변수
//    private val _UserUID=MutableLiveData<String>()
//    val UserUID: LiveData<String> = _UserUID
    var UserUID : String = ""



    fun loginWithEmailAndPassword(email: String, password:String)
    {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{signInTask ->
                //로그인 성공시
                if(signInTask.isSuccessful)
                {
                    _isLoginAvailable.value = true
                    //로그인 성공시 uid값 가져오기
                    val user = auth.currentUser
                    //UserUID.postValue(user?.uid)
                    UserUID = user?.uid.toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        getUserType()
                    }
                }
                //로그인 실패시
                else{
                    _isLoginAvailable.value=false
                }
            }
    }

    suspend fun getUserType(){
//        farmerpath.get()
//            .addOnCompleteListener{task->
//                if(task.isSuccessful){
//                    val documentSnapshot = task.result
//                    if(documentSnapshot.exists()){
//                        _isFarmer.postValue(true)
//                        Log.e("none","구인")
//                        Log.e("none","${isFarmer.value}")
//                    }
//                    else{
//                        //구인자 회원이 아님
//                        _isWorker.postValue(true)
//                    }
//                }
//                else{
//                    Log.e("none","회원정보 없음")
//                }
//            }
        UserUID?.let { uid ->
            val farmerpath = firestore.collection("Farmer").document(uid)
            val documentSnapshot = farmerpath.get().await()

            if (documentSnapshot.exists()) {
                _isFarmer.postValue(true)
                Log.e("none", "구인")
                Log.e("none", "${isFarmer.value}")
            } else {
                // 구인자 회원이 아님
                _isWorker.postValue(true)
            }
        }
    }






}
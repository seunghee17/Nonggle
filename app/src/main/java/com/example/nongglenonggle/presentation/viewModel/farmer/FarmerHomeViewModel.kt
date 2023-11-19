package com.example.nongglenonggle.presentation.viewModel.farmer

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.FarmerHomeData
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.entity.OffererHomeFilterContent
import com.example.nongglenonggle.domain.usecase.FetchFarmerDataUseCase
import com.example.nongglenonggle.domain.usecase.GetAllNoticeSubUseCase
import com.example.nongglenonggle.domain.usecase.GetAllResumeUseCase
import com.example.nongglenonggle.domain.usecase.GetBasedOnAddressUseCase
import com.example.nongglenonggle.domain.usecase.GetBasedOnCategoryUseCase
import com.example.nongglenonggle.domain.usecase.GetNoticeUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FarmerHomeViewModel @Inject constructor(
    private val fetchFarmerDataUseCase: FetchFarmerDataUseCase,
    private val getBasedOnCategoryUseCase: GetBasedOnCategoryUseCase,
    private val getBasedOnAddressUseCase: GetBasedOnAddressUseCase,
    private val getNoticeUseCase: GetNoticeUseCase,
    private val getAllResumeUseCase: GetAllResumeUseCase
)
    : ViewModel(){
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val _userDetail = MutableLiveData<FarmerHomeData?>()
    val userDetail : LiveData<FarmerHomeData?> = _userDetail

    private val _resumeNum = MutableLiveData<Int>()
    val resumeNum : LiveData<Int> = _resumeNum

    val _noticeData = MutableLiveData<NoticeContent>()
    val noticeData : LiveData<NoticeContent> = _noticeData

    //공고글이 없을때 받아올 정보들을 세팅하기 위함
    private val _basedOnCategory = MutableLiveData<List<DocumentReference>?>()
    val basedOnCategory: LiveData<List<DocumentReference>?> get() = _basedOnCategory
    val categories = mutableListOf<String>()

    //일손 데이터 유무 ui업데이트
    private val _haveData = MutableLiveData<Boolean>()
    val haveData :LiveData<Boolean> = _haveData

    private val _haveNoticeRef = MutableLiveData<Boolean>()
    val haveNoticeRef:LiveData<Boolean> = _haveNoticeRef

    //기본으로 호출되어야함
    //유저의 기본 정보 패칭용
    fun fetchUserInfo(){
        viewModelScope.launch {
            val user = fetchFarmerDataUseCase.invoke()
            _userDetail.value = user

            Log.d("fetchUserInfo","${userDetail.value?.refs}")
            setUserCategoryList()
            setRefDataCategory()
            setRefDataAddress(userDetail.value?.first!! ,userDetail.value?.second!! )
        }
    }
    private val _isNotice = MutableLiveData<Boolean>()
    val isNotice:LiveData<Boolean> = _isNotice


    fun fetchNoticeVisible(){
        _isNotice.value = true
        _resumeNum.value = 1
    }
    fun fetchNoticeGone(){
        _isNotice.value = false
        _resumeNum.value = 0
    }


    //구인자의 category항목들 리스트화
    fun setUserCategoryList(){
        if(userDetail.value?.category2 != null){
            categories.add(userDetail.value!!.category1)
            categories.add(userDetail.value!!.category2!!)
        }
        if(userDetail.value?.category3 != null){
            categories.add(userDetail.value!!.category3!!)
        }
    }

    fun setRefDataCategory() {
        viewModelScope.launch {
            val allData = mutableListOf<DocumentReference>()
            try {
                for (i in 0 until categories.size) {
                    Log.d("FarmerHomeViewModel1", "${categories[i]}")
                    val data = getBasedOnCategoryUseCase("ResumeCategory", categories[i])
                    if(data != null){
                        allData.addAll(data)
                    }
                }
                _basedOnCategory.postValue(allData)
            } catch (e: Exception) {
                Log.e("FarmerHomeViewModel1", "데이터 가져오는 중 오류 발생: $e")
            }
        }
    }

    fun setRefDataAddress(first:String, second:String){
        viewModelScope.launch {
            try{
                val data = getBasedOnAddressUseCase("ResumeFilter",first, second)
                if(data != null){
                    val currentData = _basedOnCategory.value.orEmpty()
                    val combinedData = mutableListOf<DocumentReference>()
                    combinedData.addAll(currentData)
                    combinedData.addAll(data)
                    _basedOnCategory.postValue(combinedData)
                }
            }catch (e:Exception){
                Log.e("FarmerHomeViewModel1", "데이터 가져오는 중 오류 발생: $e")
            }
        }
    }

    suspend fun setDataFromRef(documentReference:DocumentReference) : OffererHomeFilterContent?{
        return try{
            val documentSnapshot = documentReference.get().await()
            documentSnapshot.toObject(OffererHomeFilterContent::class.java)
        }catch (e:Exception){
            null
        }
    }

    suspend fun setUserFromRef(documentReference:DocumentReference) : NoticeContent?{
        return try{
            val documentSnapshot = documentReference.get().await()
            documentSnapshot.toObject(NoticeContent::class.java)
        }catch (e:Exception){
            null
        }
    }

    fun updateUI(){
        _haveData.value = true
    }
    init{
        fetchUserInfo()
        _haveData.value = false
    }

}
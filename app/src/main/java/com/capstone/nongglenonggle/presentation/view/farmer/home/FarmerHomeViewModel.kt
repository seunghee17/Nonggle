package com.capstone.nongglenonggle.presentation.view.farmer.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.domain.entity.FarmerHomeData
import com.capstone.nongglenonggle.domain.entity.NoticeContent
import com.capstone.nongglenonggle.domain.usecase.FetchFarmerDataUseCase
import com.capstone.nongglenonggle.domain.usecase.GetNoticeUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FarmerHomeViewModel @Inject constructor(
    private val fetchFarmerDataUseCase: FetchFarmerDataUseCase,
    private val getNoticeUseCase: GetNoticeUseCase,
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

            setUserCategoryList()
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
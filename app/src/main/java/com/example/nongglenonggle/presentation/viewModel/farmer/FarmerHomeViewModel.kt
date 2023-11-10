package com.example.nongglenonggle.presentation.viewModel.farmer

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.domain.entity.FarmerHomeData
import com.example.nongglenonggle.domain.entity.OffererHomeFilterContent
import com.example.nongglenonggle.domain.usecase.FetchFarmerDataUseCase
import com.example.nongglenonggle.domain.usecase.GetBasedOnAddressUseCase
import com.example.nongglenonggle.domain.usecase.GetBasedOnCategoryUseCase
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FarmerHomeViewModel @Inject constructor(
    private val fetchFarmerDataUseCase: FetchFarmerDataUseCase,
    private val getBasedOnCategoryUseCase: GetBasedOnCategoryUseCase,
    private val getBasedOnAddressUseCase: GetBasedOnAddressUseCase)
    : ViewModel(){
    private val _userDetail = MutableLiveData<FarmerHomeData?>()
    val userDetail : LiveData<FarmerHomeData?> = _userDetail

    //공고글이 없을때 받아올 정보들을 세팅하기 위함
    private val _basedOnCategory = MutableLiveData<List<DocumentReference>?>()
    val basedOnCategory: LiveData<List<DocumentReference>?> get() = _basedOnCategory
    val categories = mutableListOf<String>()

    fun fetchUserInfo(){
        viewModelScope.launch {
            val user = fetchFarmerDataUseCase.invoke()
            _userDetail.value = user
            fetchNoticeVisible()
            setUserCategoryList()
            setRefDataCategory()
//            userDetail.observeForever{data->
//                setRefDataAddress(data!!.first,data.second)
//            }
//            basedOnCategory.observeForever{data->
//                Log.d("fetchUserInfo","${data?.joinToString()}")
//                for(documentReference in basedOnCategory.value ?: emptyList()){
//                    val data = setDataFromRef(documentReference)
//                }
//            }
        }
    }
    private val _isNotice = MutableLiveData<Boolean>()
    val isNotice:LiveData<Boolean> = _isNotice
    var resumeNum:Int = 0

    fun fetchNoticeVisible(){
        if(_userDetail.value?.notice != null){
            _isNotice.value = true
            resumeNum = 1
        }
        else{
            _isNotice.value = false
            resumeNum = 0
        }
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



}
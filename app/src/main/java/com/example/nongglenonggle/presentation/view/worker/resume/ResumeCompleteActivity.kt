package com.example.nongglenonggle.presentation.view.worker.resume

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.ActivityResumeBindingImpl
import com.example.nongglenonggle.databinding.ActivityResumeCompleteBinding
import com.example.nongglenonggle.databinding.ActivityResumeCompleteBindingImpl
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.ResumeSummary
import com.example.nongglenonggle.presentation.base.BaseActivity
import com.example.nongglenonggle.presentation.view.adapter.ResumeCareerAdapter
import com.example.nongglenonggle.presentation.view.farmer.home.MainActivity
import com.example.nongglenonggle.presentation.view.worker.home.WorkerMainActivity
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeCompleteViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeCompleteViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ResumeCompleteActivity : BaseActivity<ActivityResumeCompleteBinding>(R.layout.activity_resume_complete) {
    private val viewModel : ResumeCompleteViewModel by viewModels()
    private lateinit var  adapter: ResumeCareerAdapter
    private val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.view.visibility = View.VISIBLE
        //채용제안하기 버튼
        binding.userScoreForfarmer.applyBtn.text = "채용제안하기"
        val allCareer = mutableListOf<ResumeSummary>()

        adapter = ResumeCareerAdapter(emptyList())
        binding.recyclerview.adapter= adapter

        val value = intent.getStringExtra("UID_KEY") ?: return
        viewModel.fetchResumeDetail("public", "publicResume",value)

        if(value == firebaseAuth.currentUser?.uid){
            viewModel._resumeforFarmer.postValue(false)
        }

        viewModel.resumeDetail.observe(this, Observer {resumeContent->
            try{
                Log.d("ResumeCompleteActivity","$resumeContent")
                resumeContent.resumeData?.let{resumeData->
                    adapter.updatelist(resumeData)
                }
            }catch (e:Exception){
                Log.e("ResumeCompleteActivity","$e")
            }
        })


        binding.userScoreForfarmer.applyBtn.setOnClickListener{
            //채용제안 db로직
            StoreToFarmerDB(value,firebaseAuth.currentUser!!.uid)
            val date = getCureentDate()
            storeToWorkerDB(value,firebaseAuth.currentUser!!.uid,date)
            Toast.makeText(this,"일손에게 채용제안 알림을 전송했습니다.", Toast.LENGTH_SHORT).show()
            binding.userScoreForfarmer.applyBtn.text = "채용제안 완료!"
            binding.userScoreForfarmer.applyBtn.setBackgroundResource(R.color.m3)
        }


        binding.backBtn.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun StoreToFarmerDB(worker : String, user:String){
        val suggestion = hashMapOf(
            "suggest1" to worker
        )
        firestore.collection("Farmer").document(user).set(suggestion,SetOptions.merge())
            .addOnSuccessListener {
                Log.d("StoreToFarmerDB", "success!!!")
            }
            .addOnFailureListener{e->
                Log.e("StoreToFarmerDB", "$e")
            }
    }
    fun getCureentDate():String{
        val dateFormat = SimpleDateFormat("yy.MM.dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun storeToWorkerDB(worker : String, farmer:String, date:String){
        viewModel.viewModelScope.launch{
            name = viewModel.getOffererName()
            val suggestion = hashMapOf(
                "suggest1" to farmer,
                "currentTime" to date,
                "offererName" to name
            )
            firestore.collection("Worker").document(worker).set(suggestion, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("StoreToFarmerDB", "success!!!")
                }
                .addOnFailureListener{e->
                    Log.e("StoreToFarmerDB", "$e")
                }
        }

    }


}
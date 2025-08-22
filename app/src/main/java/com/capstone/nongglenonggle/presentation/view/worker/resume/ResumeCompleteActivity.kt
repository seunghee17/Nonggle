package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.ActivityResumeCompleteBinding
import com.capstone.nongglenonggle.domain.entity.ResumeSummary
import com.capstone.nongglenonggle.presentation.base.BaseActivity
import com.capstone.nongglenonggle.presentation.view.adapter.ResumeCareerAdapter
import com.capstone.nongglenonggle.presentation.view.worker.home.WorkerMainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

        viewModel.resumeDetail.observe(this, Observer{content->
           try{
               Glide.with(binding.imageProfile).load(content.imageurl).into(binding.imageProfile)
//               if(content.careerList != null && content.careerList.size >0){
//                   val careerString = content.careerList.joinToString(", ")
//                   binding.certification.text = careerString
//               }
//               if(content.locationSelect != null && content.locationSelect.size > 0 ){
//                   val locationstring = content.locationSelect.joinToString { " " }
//                   binding.wantedLocation.text = locationstring
//               }
//               if(content.desiredItem != null && content.desiredItem.size > 0 ){
//                   val desiredstring = content.desiredItem.joinToString { ", " }
//                   binding.desiredtxt.text = desiredstring
//               }
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
            if(viewModel.resumeforFarmer.value == false) {
                Log.d("resumecomplete","worker")
                finishAffinity()
                val intent = Intent(this,WorkerMainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            else{
                //구인자측에서 클릭했다
                onBackPressed()
                Log.d("resumecomplete","farmer")
            }
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
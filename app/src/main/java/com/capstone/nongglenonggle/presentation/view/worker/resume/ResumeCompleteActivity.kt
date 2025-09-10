package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.data.model.worker.ResumeStep2UserCareerListItem
import com.capstone.nongglenonggle.databinding.ActivityResumeCompleteBinding
import com.capstone.nongglenonggle.domain.entity.ResumeSummary
import com.capstone.nongglenonggle.presentation.base.BaseActivity
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
    //private lateinit var  adapter: ResumeCareerAdapter
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

        //dapter = ResumeCareerAdapter(emptyList())
        //binding.recyclerview.adapter= adapter

        val value = intent.getStringExtra("UID_KEY") ?: return
        viewModel.fetchResumeDetail("public", "publicResume",value)

        if(value == firebaseAuth.currentUser?.uid){
            viewModel._resumeforFarmer.postValue(false)
        }

        viewModel.resumeDetail.observe(this, Observer {resumeContent->
            try{
                Log.d("ResumeCompleteActivity","$resumeContent")
                resumeContent.resumeData?.let{resumeData->
                    //adapter.updatelist(resumeData)
                }
            }catch (e:Exception){
                Log.e("ResumeCompleteActivity","$e")
            }
        })

        viewModel.resumeDetail.observe(this, Observer{content->
           try{
               Glide.with(binding.imageProfile).load(content.imageurl).into(binding.imageProfile)
               if(content.careerList != null && content.careerList.size >0){
                   val careerString = content.careerList.joinToString(", ")
                   binding.certification.text = careerString
               }
               if(content.locationSelect != null && content.locationSelect.size > 0 ){
                   val locationstring = content.locationSelect.joinToString { " " }
                   binding.wantedLocation.text = locationstring
               }
               if(content.desiredItem != null && content.desiredItem.size > 0 ){
                   val desiredstring = content.desiredItem.joinToString { ", " }
                   binding.desiredtxt.text = desiredstring
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

@Composable
fun careerItem(
    resumeUserCareerListItem: ResumeStep2UserCareerListItem,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, NonggleTheme.colors.g_line_light),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column {
            Row {
                bulletComponent()
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = resumeUserCareerListItem.careerTitle,
                    style = TextStyle(
                        fontFamily = spoqahanSansneo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        .drawBehind {
                            drawRoundRect(
                                color = Color(0xFFE5EBDD),
                                size = this.size,
                                cornerRadius = CornerRadius(4)
                            )
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = resumeUserCareerListItem.careerPeriod,
                    style = TextStyle(
                        fontFamily = spoqahanSansneo,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 10.dp),
                text = resumeUserCareerListItem.careerPeriodDetail,
                style = TextStyle(
                    fontFamily = spoqahanSansneo,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = NonggleTheme.colors.g2
                )
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = resumeUserCareerListItem.careerContent,
                style = TextStyle(
                    fontFamily = spoqahanSansneo,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = NonggleTheme.colors.g2
                )
            )
        }
    }
}

@Composable
fun bulletComponent(color: Color = NonggleTheme.colors.m1) {
    Box(
        modifier = Modifier
            .size(6.dp)
            .drawBehind {
                drawCircle(
                    color = color,
                    radius = 3f
                )
            }
    )
}
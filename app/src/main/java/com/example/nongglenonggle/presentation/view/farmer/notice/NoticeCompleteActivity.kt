package com.example.nongglenonggle.presentation.view.farmer.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.ActivityNoticeCompleteBinding
import com.example.nongglenonggle.presentation.base.BaseActivity
import com.example.nongglenonggle.presentation.view.farmer.home.MainActivity
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeCompleteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.example.nongglenonggle.presentation.view.farmer.home.FarmerhomeFragment
import com.example.nongglenonggle.presentation.view.worker.home.WorkerHomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@AndroidEntryPoint
class NoticeCompleteActivity : BaseActivity<ActivityNoticeCompleteBinding>(R.layout.activity_notice_complete) {
    private val viewModel : FarmerNoticeCompleteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val docRef = FirebaseFirestore.getInstance().collection("Worker")
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val farmerHomefragement = FarmerhomeFragment()
        val workerHomeFargment = WorkerHomeFragment()
        //농부 uid
        val uid = intent.getStringExtra("UID_KEY")
        uid?.let{
            viewModel.fetchNoticeDetail(uid)
        }

        docRef.document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnCompleteListener{task->
            if(task.isSuccessful){
                val document = task.result
                if(document != null && document.exists()){
                    //문서 id존재, 현재 회원이 구인자다
                    binding.includeUserScore.linear.visibility = View.VISIBLE
                }
                else{
                    //구직자 회원
//                    binding.close.setOnClickListener {
//                        fragmentTransaction.replace(R.id.fragment_container,workerHomeFargment)
//                        fragmentTransaction.commit()
//                    }
                }
            }else{
                Log.e("Firestore","$task.exception")
            }
        }


        binding.close.setOnClickListener{
            if(viewModel.isDataReady.value == true){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            onBackPressed()
        }
        //현재회원이 어떤 유형의 회원인지 알 방법이 필요하다
        binding.topBtn.setImageResource(R.drawable.pencil)

        viewModel.noticeDetail.asLiveData().observe(this, Observer { noticeContent->
            detectDeadline()
            detectPayType()
            setRecruitAge()
            setQualification()
            setWorkPeriod()
            setWorkTime()
            setImage()
        })

        binding.includeUserScore.applyBtn.setOnClickListener{
            Toast.makeText(this,"지원이 완료되었습니다!",Toast.LENGTH_SHORT).show()
            binding.includeUserScore.applyBtn.text = "지원완료"
            binding.includeUserScore.applyBtn.setBackgroundResource(R.color.m3)
        }
    }

    private fun detectDeadline(){
        if(viewModel.noticeDetail.value?.recruitPeriod?.get("type") == "상시모집"){
            binding.recruitDeadline.text = "상시모집"
            binding.recruitType.text = "상시모집"
        }
        else if(viewModel.noticeDetail.value?.recruitPeriod?.get("type") == "마감기한 설정"){
            binding.recruitDeadline.text = viewModel.noticeDetail?.value?.recruitPeriod?.get("detail").toString()
            binding.recruitType.text = viewModel.noticeDetail?.value?.recruitPeriod?.get("detail").toString()
        }
    }

    private fun setImage(){
        val url = viewModel.noticeDetail.value?.imageUrl
        if(url != null){
            Glide.with(this)
                .load(url)
                .into(binding.img)
        }
    }

    private fun detectPayType(){
        if(viewModel.noticeDetail.value?.pay?.get(0) == "급여협의"){
            binding.payType.text = "급여"
            binding.payNum.text = viewModel.noticeDetail.value?.pay?.get(0)
        }
        else{
            binding.payType.text = viewModel.noticeDetail.value?.pay?.get(0)
            binding.payNum.text = viewModel.noticeDetail.value?.pay?.get(1)
        }

    }
    private fun setRecruitAge(){
        var txt = "${viewModel.noticeDetail.value?.recruitAge?.get(0)}~${viewModel.noticeDetail.value?.recruitAge?.get(1)}"
        binding.recruitAge.text = txt
    }
    private fun setQualification(){
        if(viewModel.noticeDetail.value?.qualification?.get("type") == "필요없음"){
            binding.recruitQualification.text = "필요없음"
        }
        else{
            //자격증 리스트
            val map1 = viewModel.noticeDetail.value?.qualification?.get("name") as? Map<String,Any>
            val map2 = map1?.get("value") as? List<String>
            val combined = map2?.joinToString { ", " }
            binding.recruitQualification.text = combined
        }
    }

    private fun setWorkTime(){
        if(viewModel.noticeDetail.value?.workTime?.get("detail") == ""){
            binding.workTime.text = "시간협의"
            binding.workTime2.text = "시간협의"
        }
        else{
            binding.workTime.text = viewModel.noticeDetail.value?.workTime?.get("detail").toString()
            binding.workTime2.text = viewModel.noticeDetail.value?.workTime?.get("detail").toString()

        }
    }

    private fun setWorkPeriod(){
        var txt = "${viewModel.noticeDetail.value?.workPeriod?.get(0)}~${viewModel.noticeDetail.value?.workPeriod?.get(1)}"
        binding.workPeriod.text = txt
    }
}

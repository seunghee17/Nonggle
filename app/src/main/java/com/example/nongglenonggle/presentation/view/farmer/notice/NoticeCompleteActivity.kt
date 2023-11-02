package com.example.nongglenonggle.presentation.view.farmer.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
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


@AndroidEntryPoint
class NoticeCompleteActivity : BaseActivity<ActivityNoticeCompleteBinding>(R.layout.activity_notice_complete) {
    private val viewModel : FarmerNoticeCompleteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.close.setOnClickListener{
            if(viewModel.isDataReady.value == true){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        //현재회원이 어떤 유형의 회원인지 알 방법이 필요하다
        binding.topBtn.setImageResource(R.drawable.pencil)

        viewModel.noticeDetail.asLiveData().observe(this, Observer { noticeContent->
            Log.e("NoticeActivity","${noticeContent}")
            detectDeadline()
            detectPayType()
            setRecruitAge()
            setQualification()
            setWorkPeriod()
            setWorkTime()
        })
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
        if(viewModel.noticeDetail.value?.workTime?.get("detail") == null){
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

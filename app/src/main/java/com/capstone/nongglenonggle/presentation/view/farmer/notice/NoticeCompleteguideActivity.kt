package com.capstone.nongglenonggle.presentation.view.farmer.notice

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.ActivityNoticeCompleteguideBinding
import com.capstone.nongglenonggle.presentation.base.BaseActivity
import com.capstone.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeCompleteViewModel
import com.google.firebase.auth.FirebaseAuth

class NoticeCompleteguideActivity : BaseActivity<ActivityNoticeCompleteguideBinding>(R.layout.activity_notice_completeguide) {
    private val viewModel: FarmerNoticeCompleteViewModel by viewModels()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTextColor(binding.first, binding.first.text.toString(), "공고쓰기")
        val uid = firebaseAuth.currentUser?.uid!!

        binding.completeBtn.setOnClickListener{
            val intent = Intent(this, NoticeCompleteActivity::class.java)
            intent.putExtra("UID_KEY", uid)
            startActivity(intent)
        }
    }

    private fun setTextColor(textView: TextView, fullText:String, wordsToColor:String){
        val color = ContextCompat.getColor(textView.context, R.color.m1)
        val spannableStringBuilder = SpannableStringBuilder(fullText)
        var startIndex = fullText.indexOf(wordsToColor)
        while(startIndex != -1){
            val endIndex = startIndex + wordsToColor.length
            spannableStringBuilder.setSpan(ForegroundColorSpan(color), startIndex,endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            startIndex = fullText.indexOf(wordsToColor, startIndex+1)
        }
        textView.text = spannableStringBuilder
    }
}
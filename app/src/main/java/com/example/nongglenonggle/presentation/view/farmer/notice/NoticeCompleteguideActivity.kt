package com.example.nongglenonggle.presentation.view.farmer.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.ActivityNoticeCompleteguideBinding
import com.example.nongglenonggle.presentation.base.BaseActivity
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeCompleteViewModel

class NoticeCompleteguideActivity : BaseActivity<ActivityNoticeCompleteguideBinding>(R.layout.activity_notice_completeguide) {
    private val viewModel: FarmerNoticeCompleteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTextColor(binding.first, binding.first.text.toString(), "공고쓰기")

        binding.completeBtn.setOnClickListener{
            val intent = Intent(this, NoticeCompleteActivity::class.java)
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
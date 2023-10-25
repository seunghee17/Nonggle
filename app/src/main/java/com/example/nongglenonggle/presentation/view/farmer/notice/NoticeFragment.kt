package com.example.nongglenonggle.presentation.view.farmer.notice

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentNoticeBinding
import com.example.nongglenonggle.presentation.view.farmer.home.MainActivity

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gotoN.setOnClickListener{
            val intent = Intent(context, NoticeActivity::class.java)
            startActivity(intent)
        }
        binding.close.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        setTextColor(binding.title, binding.title.text.toString(), "공고글 작성은")

    }

    private fun setTextColor(textView:TextView, fullText:String, wordsToColor:String){
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
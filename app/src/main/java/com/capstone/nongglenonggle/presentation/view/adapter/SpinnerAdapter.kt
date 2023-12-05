package com.capstone.nongglenonggle.presentation.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.capstone.nongglenonggle.R

class SpinnerAdapter(context: Context, @LayoutRes private val resId:Int, private val List:Array<String>, private val textViewResId:Int)
    :ArrayAdapter<String>(context,resId,List)
{
    private var hintText:String?=null
    private var hintColor:Int=0

    fun setHintTextColor(text:String, colorResId:Int){
        hintText = text
        hintColor = ContextCompat.getColor(context,colorResId)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = super.getView(position, convertView, parent)
        val textView= v.findViewById<TextView>(textViewResId)

        if(position==count){
            textView.text=""
            textView.hint = hintText
            if(hintColor != 0){
                textView.setHintTextColor(hintColor)
            }
            else{
                textView.text = getItem(position)
                textView.hint = null
            }
        }

        return v
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        view.background = ContextCompat.getDrawable(context, R.drawable.bg_spinner_dropdown)

        //마지막 항목을 드롭다운 목록에서 숨긴다
        if(position == List.size-1){
            val empty = TextView(context)
            empty.height = 0
            return empty
        }
        return view
    }


    override fun getCount(): Int{
        //마지막 항목은 포함하지 않도록 한다
        return super.getCount() -1
    }

}
package com.example.nongglenonggle.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.databinding.adapters.SpinnerBindingAdapter
import com.example.nongglenonggle.databinding.ItemSpinnerBinding

class SpinnerAdapter(context: Context, @LayoutRes private val resId:Int, private val List:List<String>)
    :ArrayAdapter<String>(context,resId,List)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        binding.listContent.text = List[position]

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        binding.listContent.text = List[position]

        return binding.root
    }

    override fun getCount()= List.size
}
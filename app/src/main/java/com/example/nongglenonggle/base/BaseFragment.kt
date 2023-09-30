package com.example.nongglenonggle.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes private val layoutRes: Int): Fragment(){
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //DataBinding 객체 초기화
        _binding = DataBindingUtil.inflate(inflater,layoutRes,container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner
        return _binding?.root
    }

    //fragment교체
    protected fun replaceFragment(fragment:Fragment, containerId:Int, addToBackStack: Boolean = false)
    {
        val transaction = parentFragmentManager.beginTransaction()
            .replace(containerId, fragment)
        if(addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    //fragment추가
    protected fun addFragment(fragment: Fragment, containerId: Int, addToBackStack: Boolean = false)
    {
        val transaction = parentFragmentManager.beginTransaction()
            .add(containerId,fragment)
        if(addToBackStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }
}
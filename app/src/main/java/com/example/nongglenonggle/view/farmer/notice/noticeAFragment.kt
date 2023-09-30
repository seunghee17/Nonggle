package com.example.nongglenonggle.view.farmer.notice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentNoticeABinding
import com.example.nongglenonggle.viewModel.farmer.FarmerNoticeViewModel
class noticeAFragment : BaseFragment<FragmentNoticeABinding>(R.layout.fragment_notice_a) {
    private val viewModel: FarmerNoticeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("shit", "oncreate")
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
        binding.viewModel = viewModel
        Log.e("shit", "onviewcreate")

        val sharedPreferences = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor= sharedPreferences.edit()
        editor.clear()
        editor.apply()

        binding.namebox.setOnFocusChangeListener{
            view,isfocus ->
            viewModel._isClick1.postValue(isfocus)
        }




        binding.namebox.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.namebox.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.isClick1.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.namebox.text != null){
                        binding.namebox.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                        binding.namebox.getClearButton(R.drawable.xcircle)
                    }
                    else{
                        binding.namebox.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                }
            }
        })


        binding.phnumbox.setOnFocusChangeListener { view, isfocus ->
            viewModel._isClick2.postValue(isfocus)
        }
        binding.phnumbox.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.phnumbox.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.isClick2.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.phnumbox.text!=null){
                        binding.phnumbox.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                        binding.phnumbox.getClearButton(R.drawable.xcircle)
                    }
                    else{
                        binding.phnumbox.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                }
            }
        })

        binding.address.setOnClickListener{
            moveToWeb()
        }

        viewModel.AddressFromWeb.observe(viewLifecycleOwner){isdata->
            if(viewModel.AddressFromWeb.value != null){
                viewModel._isAddressVisible.postValue(true)
                binding.addressFromweb.text = viewModel.AddressFromWeb.value
                binding.address.hint = ""
            }
        }


        binding.detailAddress.setOnFocusChangeListener { view, isfocus ->
            viewModel._isClick3.postValue(isfocus)
        }

        binding.detailAddress.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.detailAddress.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.isClick3.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.detailAddress.text != null){
                        binding.detailAddress.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                        binding.detailAddress.getClearButton(R.drawable.xcircle)
                    }
                    else{
                        binding.detailAddress.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                    Log.e("fucking","${viewModel.AddressFromWeb.value}")
                }
                viewModel.updateNext()
            }
        })

        binding.nextBtn.setOnClickListener{
            //viewModel.switchTab.value = true
            val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewpager)
            val current = viewpager.currentItem
            val next = current+1
            if(next < viewpager.adapter?.itemCount ?: 0){
                viewpager.setCurrentItem(next,true)
            }else{
                Log.e("yet","아직 마지막아님")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("shit", "onresume")
        viewModel.loadAddressData(requireContext())
    }

//    override fun onStart() {
//        super.onStart()
//        Log.e("shit", "onstart")
//    }

    //삭제 버튼의 동작을 함수화
    fun EditText.getClearButton(drawableRightId:Int){
        val drawableRight = 2
        val rightDrawable = this.compoundDrawables[drawableRight]
        if (rightDrawable != null) {
            val drawableWidth = rightDrawable.bounds.width()
            val clearButtonStart = this.width - drawableWidth

            this.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= clearButtonStart) {
                        this.text.clear()
                        return@setOnTouchListener true
                    }
                }
                false
            }
        }
    }




    fun moveToWeb(){
        //replaceFragment(AddressSettingFragment(), R.id.addressSearch)
        val intent = Intent(context,AddressSearchWebActivity::class.java)
        startActivity(intent)
    }



}
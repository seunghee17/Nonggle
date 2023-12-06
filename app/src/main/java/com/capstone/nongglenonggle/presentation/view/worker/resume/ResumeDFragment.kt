package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentResumeDBinding
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.util.hideClearButton
import com.capstone.nongglenonggle.presentation.util.showClearButton
import com.capstone.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import com.capstone.nongglenonggle.presentation.view.dialog.LocationSelectFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ResumeDFragment : BaseFragment<FragmentResumeDBinding>(R.layout.fragment_resume_d) {
    private val viewModel: ResumeViewModel by activityViewModels()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var opensetting1 = ""
    var opensetting2 = ""

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
        binding.viewModel = viewModel
        val uid = firebaseAuth.currentUser?.uid!!

        //기타품목 나타내기용
        viewModel.categoryLive.observe(viewLifecycleOwner, Observer { list->
            list[7].let{
                if(it){
                    binding.addtionalEdit.visibility = View.VISIBLE
                }else{
                    binding.addtionalEdit.visibility = View.GONE
                }
            }
        })

        binding.addtionalEdit.setOnFocusChangeListener{view,isfocus->
            viewModel.setAdditional(isfocus)
            if(isfocus && binding.addtionalEdit.text?.isNotEmpty() == true){
                binding.addtionalEdit.showClearButton(R.drawable.xcircle)
            }else{
                binding.addtionalEdit.hideClearButton()
            }
        }
        binding.addtionalEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.addtionalEdit.isFocused && s?.isNotEmpty() == true) {
                    binding.addtionalEdit.showClearButton(R.drawable.xcircle)
                } else {
                    binding.addtionalEdit.hideClearButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.focusAdditional.observe(viewLifecycleOwner){focus->
                    if(focus && binding.addtionalEdit.text != null){
                        binding.addtionalEdit.showClearButton(R.drawable.xcircle)
                    }else{
                        binding.addtionalEdit.hideClearButton()
                    }
                }
                viewModel.additionalTxt = s.toString()
            }

        })
        binding.dormA.setOnClickListener{
            viewModel._clickDormA.postValue(true)
            viewModel._clickDormB.postValue(false)
            viewModel.dormType = binding.dormA.text.toString()
        }
        binding.dormB.setOnClickListener{
            viewModel._clickDormA.postValue(false)
            viewModel._clickDormB.postValue(true)
            viewModel.dormType = binding.dormB.text.toString()
        }
        initSpinner(binding.spinner, R.array.select_resume_day, "희망하는 근무요일을 선택해주세요.", viewModel._activedaySpinner)

        binding.spinnerEdit.setOnFocusChangeListener{view,focus->
            viewModel.setEditFocus(focus)
            if(focus && binding.spinnerEdit.text?.isNotEmpty() == true){
                binding.spinnerEdit.showClearButton(R.drawable.xcircle)
            }else{
                binding.spinnerEdit.hideClearButton()
            }
        }
        binding.spinnerEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.spinnerEdit.isFocused && s?.isNotEmpty() == true ){
                    binding.addtionalEdit.showClearButton(R.drawable.xcircle)
                }else{
                    binding.addtionalEdit.hideClearButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.focusSpinnerEdit.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.spinnerEdit.text!=null){
                        binding.spinnerEdit.showClearButton(R.drawable.xcircle)
                    }else{
                        binding.spinnerEdit.hideClearButton()
                    }
                }
                viewModel.dayDetailData = s.toString()
            }

        })
        binding.privateBtn.setOnClickListener{
            viewModel._activeprivate.postValue(true)
            viewModel._activepublic.postValue(false)
            //비공개 설정시 추가적 기능 명세
            opensetting1 = "private"
            opensetting2 = "privateresume"
        }
        binding.publicBtn.setOnClickListener{
            viewModel._activeprivate.postValue(false)
            viewModel._activepublic.postValue(true)
            opensetting1 = "public"
            opensetting2 = "publicResume"
        }
        binding.selectLocation.setOnTouchListener{view,event->
            //희망 장소 선택
            if(event.action == MotionEvent.ACTION_UP){
                showLocationSelect()
            }
            false
        }

        viewModel.locationSelect.observe(viewLifecycleOwner){list->
            if(list.size == 0){
                binding.flexbox.visibility = View.GONE
            }
            if(list.size ==2){
                binding.flexbox.visibility = View.VISIBLE
                binding.locationA.visibility = View.VISIBLE
                binding.locationATxt.text = "${viewModel.locationSelect.value?.get(0)} ${viewModel.locationSelect.value?.get(1)}"
            }
            if(list.size == 4){
                binding.locationB.visibility = View.VISIBLE
                binding.locationBTxt.text = "${viewModel.locationSelect.value?.get(2)} ${viewModel.locationSelect.value?.get(3)}"
            }
            if(list.size==6){
                binding.locationC.visibility = View.VISIBLE
                binding.locationCTxt.text = "${viewModel.locationSelect.value?.get(4)} ${viewModel.locationSelect.value?.get(5)}"
            }
        }
        binding.complete.setOnClickListener{
            lifecycleScope.launch {
                val result = viewModel.setResumeData()
                if(result != null){
                    viewModel.addResumeContent(result,opensetting1,opensetting2)
                    Toast.makeText(context, "이력서 작성을 완료했습니다!", Toast.LENGTH_SHORT).show()
                    activity?.finishAffinity()
                    val intent = Intent(context,ResumeCompleteActivity::class.java)
                    intent.putExtra("setting1", opensetting1)
                    intent.putExtra("setting2", opensetting2)
                    intent.putExtra("UID_KEY", uid)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                else{
                    Log.e("ResumeDFragment","받아온 데이터가 없습니다")
                }
            }
            }
    }
    // 스피너 어댑터 초기화 함수
    private fun initSpinner(spinner: Spinner, itemsArrayId: Int, hintText: String, viewModelLiveData: MutableLiveData<Boolean>) {
        val items = resources.getStringArray(itemsArrayId)
        val adapter = com.capstone.nongglenonggle.presentation.view.adapter.SpinnerAdapter(requireContext(), R.layout.item_spinner, items, R.id.list_content)
        adapter.setHintTextColor(hintText, R.color.g3)
        spinner.adapter = adapter
        spinner.setSelection(adapter.count)

        spinner.post {
            spinner.dropDownVerticalOffset = spinner.height
        }

        spinner.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                viewModelLiveData.postValue(true)
                binding.spinnerEdit.visibility = View.VISIBLE
            }
            false
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModelLiveData.postValue(false)
                viewModel.dayData = items[p2]
                p1?.isPressed = false
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModelLiveData.postValue(false)
            }
        }
    }

    private fun showLocationSelect() {
        val newFrament = LocationSelectFragment()
        newFrament.show(parentFragmentManager,"locationselectfragment")
    }

}
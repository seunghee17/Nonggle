package com.example.nongglenonggle.presentation.view.worker.resume

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentResumeABinding
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import com.example.nongglenonggle.presentation.util.hideClearButton
import com.example.nongglenonggle.presentation.util.setupClearButton
import com.example.nongglenonggle.presentation.util.showClearButton
import com.example.nongglenonggle.presentation.view.dialog.DatepickerFragment

@AndroidEntryPoint
class ResumeAFragment : BaseFragment<FragmentResumeABinding>(R.layout.fragment_resume_a) {
    private val viewModel:ResumeViewModel by activityViewModels()
    var pickImageFromAlbum = 1
    var fbStorage : FirebaseStorage? = null
    var uriPhoto : Uri? = null
    private val REQUEST_CODE_PERMISSION = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //사진 업로드
        //storage초기화
        fbStorage = FirebaseStorage.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= super.onCreateView(inflater, container, savedInstanceState)

        binding.profileUpload.setOnClickListener{
            if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                openGallery()
            }
            else{
                //권한 요청
                requestStoragePermission()
            }
        }
        return view
    }
    private fun openGallery(){
        //open album
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, pickImageFromAlbum)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.nameEdit.setOnFocusChangeListener{view,isfocus->
            viewModel.setFocus(isfocus)
            if(isfocus && binding.nameEdit.text?.isNotEmpty() == true){
                binding.nameEdit.showClearButton(R.drawable.xcircle)
            }else{
                binding.nameEdit.hideClearButton()
            }
        }
        binding.nameEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.nameEdit.isFocused && s?.isNotEmpty() == true) {
                    binding.nameEdit.showClearButton(R.drawable.xcircle)
                } else {
                    binding.nameEdit.hideClearButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeFocus.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.nameEdit.text!=null){
                        binding.nameEdit.showClearButton(R.drawable.xcircle)
                    }else{
                        binding.nameEdit.hideClearButton()
                    }
                }
                viewModel.userName = s.toString()
            }

        })

        //생년월일
        binding.birthContainer.setOnTouchListener{v,event->
            if(event.action == MotionEvent.ACTION_UP){
                showDatePicker()
            }
            false
        }
        viewModel.BirthList.observe(viewLifecycleOwner){isdata->
            binding.birthTxt.text = "${viewModel.BirthList.value?.get(0)}년 ${viewModel.BirthList.value?.get(1)}월 ${viewModel.BirthList.value?.get(2)}일"
            viewModel.setActive()
            viewModel.calculate(viewModel.BirthList.value?.get(0)!!)
        }
        binding.certifiYes.setOnClickListener{
            viewModel.activeCertifiA()
        }
        binding.certifiNo.setOnClickListener{
            viewModel.activeCertifiB()
        }

        binding.women.setOnClickListener{
            viewModel.activeWomen()
        }
        binding.man.setOnClickListener{
            viewModel.activeMan()
        }

        binding.certificationTxt.setOnFocusChangeListener{view,isfocus->
            viewModel.setFocusCertifi(isfocus)
            if(isfocus && binding.certificationTxt.text?.isNotEmpty()==true){
                binding.certificationTxt.showClearButton(R.drawable.xcircle)
            }
            else{
                binding.certificationTxt.hideClearButton()
            }
        }
        binding.certificationTxt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.certificationTxt.isFocused && s?.isNotEmpty() == true) {
                    binding.certificationTxt.showClearButton(R.drawable.xcircle)
                    viewModel._changeConfirmCertifi.postValue(true)
                } else {
                    binding.certificationTxt.hideClearButton()
                    viewModel._changeConfirmCertifi.postValue(false)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeFocusCertifi.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.certificationTxt.text!=null){
                        binding.certificationTxt.showClearButton(R.drawable.xcircle)
                        viewModel._changeConfirmCertifi.postValue(true)
                    }else{
                        binding.certificationTxt.hideClearButton()
                        viewModel._changeConfirmCertifi.postValue(false)
                    }
                }
                binding.confirmBtn.setOnClickListener{
                    viewModel.storeCarrer(s.toString())
                    binding.certificationTxt.text.clear()
                }
            }


        })

        viewModel.CarrerList.observe(viewLifecycleOwner,{list->
            if(list.isNotEmpty() && list.size==1){
                binding.flexBox.visibility = View.VISIBLE
                binding.certifiA.visibility = View.VISIBLE
                binding.certifiATxt.text = viewModel.CarrerList.value?.get(0)
            }
            else if(list.isNotEmpty() && list.size==2){
                binding.certifiB.visibility = View.VISIBLE
                binding.certifiBTxt.text = viewModel.CarrerList.value?.get(1)
            }
            else if(list.isNotEmpty() && list.size==3){
                binding.certifiC.visibility = View.VISIBLE
                binding.certifiCTxt.text = viewModel.CarrerList.value?.get(2)
            }
            else if(list.isEmpty()){
                binding.flexBox.visibility = View.GONE
            }
        })
        binding.certifiAClose.setOnClickListener{
            viewModel.removeCarrer(0)
        }
        binding.certifiBClose.setOnClickListener{
            viewModel.removeCarrer(1)
            binding.certifiB.visibility = View.GONE
        }
        binding.certifiCClose.setOnClickListener{
            viewModel.removeCarrer(2)
            binding.certifiC.visibility = View.GONE
        }
        binding.nextBtn.setOnClickListener{
            val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
            val current = viewpager.currentItem
            val next = current+1
            if(next < viewpager.adapter?.itemCount ?: 0){
                viewpager.setCurrentItem(next,true)
            }else{
                Log.e("yet","아직 마지막아님")
            }
        }

    }

    private fun showDatePicker() {
        val newFrament = DatepickerFragment()
        newFrament.show(parentFragmentManager,"datepicker")
    }


    //권한이 없을때 해당함수 호출
    private fun requestStoragePermission(){
        requestPermissions(
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_PERMISSION){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery()
            }else{
                //권한 거부처리
                Log.d("image","권한 거부처리")
                openGallery()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == pickImageFromAlbum){
            if(resultCode == Activity.RESULT_OK){
                uriPhoto = data?.data
                //화면에 사진을 보여준다
                binding.profileUpload.setImageURI(uriPhoto)
                uriPhoto?.let{uri->
                    val imageEntity = Model.ImageEntity(uri)
                    viewModel.uploadImage(imageEntity)
                }
            }
            else{
                //예외처리
                Log.d("image","예외")
            }
        }
    }


}
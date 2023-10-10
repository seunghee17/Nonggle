package com.example.nongglenonggle.presentation.view.worker.resume

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentResumeABinding
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeAFragment : BaseFragment<FragmentResumeABinding>(R.layout.fragment_resume_a) {
    private val viewModel:ResumeViewModel by activityViewModels()
    var pickImageFromAlbum = 0
    var fbStorage : FirebaseStorage? = null
    var uriPhoto : Uri? = null
    private val REQUEST_CODE_PERMISSION = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= super.onCreateView(inflater, container, savedInstanceState)
        //사진 업로드
        //storage초기화
        fbStorage = FirebaseStorage.getInstance()
        binding.profileUpload.setOnClickListener{
            if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                //open album
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, pickImageFromAlbum)
            }
            else{
                //권한 요청
                requestStoragePermission()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.nameEdit.setOnFocusChangeListener{view,isfocus->
            viewModel.setFocus()
        }
        binding.nameEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.nameEdit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeFocus.observe(viewLifecycleOwner){isfocus->
                    if(isfocus && binding.nameEdit.text!=null){
                        binding.nameEdit.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.xcircle,0)
                        binding.nameEdit.getClearButton(R.drawable.xcircle)
                    }else{
                        binding.nameEdit.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    }
                }
                viewModel.userName = s.toString()
            }

        })
    }

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

    //권한이 없을때 해당함수 호출
    private fun requestStoragePermission(){
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_PERMISSION
        )
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

            }
        }
    }


}
package com.example.nongglenonggle.presentation.view.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentSignupDBinding
import com.example.nongglenonggle.presentation.view.login.LoginActivity
import com.example.nongglenonggle.presentation.viewModel.signup.SignupViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupDFragment : BaseFragment<FragmentSignupDBinding>(R.layout.fragment_signup_d) {
    private val viewModel: SignupViewModel by activityViewModels()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()
    val uid = user?.uid
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
        var stringArray:List<String>
        val addressSearch = binding.addressSearch
        addressSearch.setOnClickListener{
            moveToNext()
        }

        viewModel.addressResult.observe(viewLifecycleOwner){ addressResult->
            //선택한 주소 입력됨
            if(addressResult != null){
                binding.firstaddressTxt.text = viewModel.addressResult.value
                binding.addressSearch.hint=""
                stringArray =splitString(addressResult)
                //이제 개인정보 넣기 위해 호출해야함
                val addressData = hashMapOf(
                    "first" to stringArray[0],
                    "second" to stringArray[1]
                )
                firestore.collection("Farmer").document(uid!!).set(addressData, SetOptions.merge())
            }

        }
        binding.nextBtn.setOnClickListener{
            if(viewModel.dstepActive.value == true) {
                setToFirestore(viewModel.selectedButtonText)
                moveToEnd()
            }
        }

    }
    fun splitString(input:String):List<String>{
        return input.split(" ")
    }
    fun setToFirestore(data : MutableList<String>){
        val finalData = hashMapOf(
            "category1" to data[0],
            "category2" to data[1],
            "category3" to data[2]
        )
        firestore.collection("Farmer").document(uid!!).set(finalData, SetOptions.merge())
    }
    fun moveToNext() {
        //replaceFragment(AddressSearchFragment(), R.id.signup_fragmentcontainer)
        findNavController().navigate(R.id.action_signupDFragment_to_addressSearchFragment)
    }
    fun moveToEnd(){
        val intent = Intent(context,LoginActivity::class.java)
        startActivity(intent)
    }

}
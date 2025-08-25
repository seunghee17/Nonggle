package com.capstone.nongglenonggle.presentation.view.farmer.search

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentFarmerSearchBinding
import com.capstone.nongglenonggle.domain.entity.OffererHomeFilterContent
import com.capstone.nongglenonggle.domain.entity.OffererSearchFilterModel
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.view.adapter.FilterFarmerHomeAdapter
import com.capstone.nongglenonggle.presentation.view.adapter.FilterFarmerSearchAdapter
import com.capstone.nongglenonggle.presentation.view.dialog.FilterBottomSheetFragment
import com.capstone.nongglenonggle.presentation.view.worker.resume.ResumeCompleteActivity
import com.capstone.nongglenonggle.presentation.view.farmer.home.FarmerHomeViewModel
import com.capstone.nongglenonggle.presentation.viewModel.farmer.FarmerSearchViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.ResumeCompleteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchWorkerFragment : BaseFragment<FragmentFarmerSearchBinding>(R.layout.fragment_farmer_search) {
    private val viewModel: FarmerSearchViewModel by viewModels()
    private val homeVM: FarmerHomeViewModel by activityViewModels()
    private val completeVM : ResumeCompleteViewModel by activityViewModels()

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var adapter1 : FilterFarmerHomeAdapter
    private lateinit var adapter2 : FilterFarmerSearchAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter1 = FilterFarmerHomeAdapter(emptyList(), object :
            FilterFarmerHomeAdapter.onItemClickListener{
            override fun onItemClickListener(uid: String) {
                //리스트의 항목 선택시 동작 정의
                val intent = Intent(context, ResumeCompleteActivity::class.java)
                intent.putExtra("UID_KEY",uid)
                completeVM._resumeforFarmer.postValue(true)
                startActivity(intent)
            }
            })

        adapter2 = FilterFarmerSearchAdapter(emptyList() , object : FilterFarmerSearchAdapter.onItemClickListener{
            override fun onItemClickListener(uid: String) {
                val intent = Intent(context, ResumeCompleteActivity::class.java)
                intent.putExtra("UID_KEY",uid)
                completeVM._resumeforFarmer.postValue(true)
                startActivity(intent)
            }
        })

        binding.mainRecycler.adapter = adapter1
        binding.subRecycler.adapter = adapter2


        val ref = firebaseAuth.uid?.let { firestore.collection("Farmer").document(it) }

        //유저이름표시
        ref?.get()?.addOnSuccessListener { documentSnapshot->
            if(documentSnapshot != null && documentSnapshot.exists()){
                val name = documentSnapshot.getString("userName")
                viewModel.updateInfo(name!!)
            }

        }

        binding.filterBtn.setOnClickListener{
            setupBottomSheet()
        }


        homeVM.basedOnCategory.observe(viewLifecycleOwner){doc->
            homeVM.viewModelScope.launch {
                val dataList = mutableListOf<OffererHomeFilterContent>()
                for(docRef in homeVM.basedOnCategory.value ?: emptyList()){
                    val data = homeVM.setDataFromRef(docRef)
                    data?.let {
                        dataList.add(it)
                    }
                }
                adapter1.updateList(dataList)
            }
        }

        firestore.collection("Resume").document("public").collection("publicResume").get().addOnSuccessListener {
            documents->
            val items = documents.mapNotNull {documentSnapshot->
                documentSnapshot.toObject(OffererSearchFilterModel::class.java)?.copy(uid = documentSnapshot.id)
            }
            adapter2.updateList(items)
        }

        setTextColor(binding.title, binding.title.text.toString(), listOf("일손을",))
    }

    private fun setupBottomSheet(){
        val bottomsheet = FilterBottomSheetFragment()
        bottomsheet.show(parentFragmentManager,bottomsheet.tag)
    }

    private fun setTextColor(textView: TextView, fullText:String, wordsToColor:List<String>){
        val color = ContextCompat.getColor(textView.context, R.color.m1)
        val spannableStringBuilder = SpannableStringBuilder(fullText)
        for(word in wordsToColor){
            var startIndex = fullText.indexOf(word)
            while(startIndex != -1){
                val endIndex = startIndex + word.length
                spannableStringBuilder.setSpan(
                    ForegroundColorSpan(color),
                    startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                startIndex = fullText.indexOf(word, startIndex+1)
            }
        }
        textView.text = spannableStringBuilder
    }

}
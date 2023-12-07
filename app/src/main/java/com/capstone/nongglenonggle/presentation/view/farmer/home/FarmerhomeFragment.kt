package com.capstone.nongglenonggle.presentation.view.farmer.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.capstone.nongglenonggle.domain.entity.OffererHomeFilterContent
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.view.FirstActivity
import com.capstone.nongglenonggle.presentation.view.adapter.FilterFarmerHomeAdapter
import com.capstone.nongglenonggle.presentation.viewModel.farmer.FarmerHomeViewModel
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentFarmerHomeBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FarmerhomeFragment : BaseFragment<FragmentFarmerHomeBinding>(R.layout.fragment_farmer_home) {
    private val viewModel: FarmerHomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchUserInfo()
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
        binding.lifecycleOwner = this


        //-------------------삭제될 코드--------------------------
        binding.logo.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            activity?.finishAffinity()
            val intent = Intent(requireContext(), FirstActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        //-------------------삭제될 코드--------------------------


        val adapter = FilterFarmerHomeAdapter(emptyList(), object : FilterFarmerHomeAdapter.onItemClickListener{
            override fun onItemClickListener(uid: String) {
                //리스트 선택시 동작 정의
            }
        }
        )
        binding.recyclerWorker.adapter = adapter
        setupInit()
        observe()

        viewModel.basedOnCategory.observe(viewLifecycleOwner){doc->
            viewModel.updateUI()
            viewModel.viewModelScope.launch {
                val dataList = mutableListOf<OffererHomeFilterContent>()
                for(documentReference in viewModel.basedOnCategory.value ?: emptyList()){
                    val data = viewModel.setDataFromRef(documentReference)
                    data?.let{
                        dataList.add(it)
                    }
                }
                adapter.updateList(dataList)
            }
        }
        binding.toSearch.setOnClickListener{
            findNavController().navigate(R.id.searchWorkerFragment)
        }
    }


    fun setupInit(){
        viewModel.fetchNoticeVisible()
    }

    fun observe(){
        viewModel.userDetail.observe(viewLifecycleOwner){data->
            if(data?.refs == null){
                //공고글 없을때
                viewModel.fetchNoticeGone()
                binding.info.text = "공고글 작성하기"
                binding.num.visibility = View.GONE
            }
            else{
                //공고글 있을떼
                //notice정보 패칭하는 함수 호출
                viewModel.viewModelScope.launch {
                    viewModel.fetchNoticeVisible()
                    val data = viewModel.setUserFromRef(data?.refs.get(0))
                    viewModel._noticeData.value = data
                }
                Log.d("resumenum","${viewModel.resumeNum}")
                binding.info.text = "지원자 수"
                binding.num.visibility = View.VISIBLE
            }
        }
        //홈화면 이력서 미리보기
        viewModel.noticeData.observe(viewLifecycleOwner){noticeContent ->
            binding.yesNotice.title.text = noticeContent.title
            if(noticeContent.recruitPeriod["type"].toString() == "상시모집") {
                binding.yesNotice.deadline.text = "상시모집"
            }else{
                binding.yesNotice.deadline.text = noticeContent.recruitPeriod["detail"].toString()
            }
            when(noticeContent.categoryItem.get(0)){
                "식량작물" -> binding.yesNotice.image.setImageResource(R.drawable.img_offer_rice)
                "채소" -> binding.yesNotice.image.setImageResource(R.drawable.img_offer_greens)
                "과수" -> binding.yesNotice.image.setImageResource(R.drawable.appleexample)
                "특용작물" -> binding.yesNotice.image.setImageResource(R.drawable.img_offer_cashcrop)
                "화훼" -> binding.yesNotice.image.setImageResource(R.drawable.img_offer_flower)
                "축산" -> binding.yesNotice.image.setImageResource(R.drawable.img_offer_animal)
                "농기계작업" -> binding.yesNotice.image.setImageResource(R.drawable.img_offer_car)
                else-> binding.yesNotice.image.setImageResource(R.drawable.img_offer_etc)
            }
        }
    }


}
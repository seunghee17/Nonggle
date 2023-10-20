package com.example.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentLocationSelectBinding
import com.example.nongglenonggle.databinding.FragmentTimepickerBinding
import com.example.nongglenonggle.presentation.view.adapter.RegionFirstAdapter
import com.example.nongglenonggle.presentation.view.adapter.RegionSecondAdapter
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class LocationSelectFragment : BottomSheetDialogFragment() {
    private val viewModel: ResumeViewModel by activityViewModels()
    private var _binding: FragmentLocationSelectBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RegionFirstAdapter
    private val adapter2 = RegionSecondAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationSelectBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.closebtn.setOnClickListener {
            dismiss()
        }

        val recyclerview1 = binding.firstLocation
        val firstRegion = resources.getStringArray(R.array.all).toList()

        val adapter1 = RegionFirstAdapter(firstRegion)
        recyclerview1.adapter = adapter1



        adapter1.itemClickListener = { position, value ->
            when (value) {
                "서울" -> setNewList(R.array.seoul)
                "강원" -> setNewList(R.array.kangwon)
                "경기" -> setNewList(R.array.kyonggi)
                "경남" -> setNewList(R.array.kyongnam)
                "경북" -> setNewList(R.array.kyongbook)
                "광주" -> setNewList(R.array.kwangju)
                "대구" -> setNewList(R.array.daegu)
                "대전" -> setNewList(R.array.daejun)
                "부산" -> setNewList(R.array.busan)
                "세종" -> {
                }

                "울산" -> setNewList(R.array.ulsan)
                "인천" -> setNewList(R.array.incheon)
                "전남" -> setNewList(R.array.jeonnam)
                "전북" -> setNewList(R.array.jeonbook)
                "제주" -> setNewList(R.array.jeju)
                "충남" -> setNewList(R.array.choognam)
                "충북" -> setNewList(R.array.choogbook)
            }
        }
        binding.confirmButton.setOnClickListener() {
            Log.d("LocationSelectFragment", "Before getting text1: ${adapter1.getSelectedText()}")
            val selectedText1 = adapter1.getSelectedText()
            if (selectedText1 != null) {
                viewModel.storeLocation(selectedText1)
            }
            val selectedText2 = adapter2.getSelectedText()
            if (selectedText2 != null) {
                viewModel.storeLocation(selectedText2)
            }
        }

        viewModel.locationSelect.observe(viewLifecycleOwner){
            if(viewModel.locationSelect.value?.size == 2){
                binding.locationBox.visibility = View.VISIBLE
                binding.wantedLocation1.visibility = View.VISIBLE
                binding.wantedLocation1Txt.text = "${viewModel.locationSelect.value?.get(0)} ${viewModel.locationSelect.value?.get(1)}"
            }
            if(viewModel.locationSelect.value?.size == 4){
                binding.wantedLocation2.visibility = View.VISIBLE
                binding.wantedLocation2Txt.text = "${viewModel.locationSelect.value?.get(2)} ${viewModel.locationSelect.value?.get(3)}"
            }
            if(viewModel.locationSelect.value?.size == 6){
                binding.wantedLocation3.visibility = View.VISIBLE
                binding.wantedLocation3Txt.text = "${viewModel.locationSelect.value?.get(4)} ${viewModel.locationSelect.value?.get(5)}"
            }
            if(viewModel.locationSelect.value?.size == 0){
                binding.locationBox.visibility = View.GONE
            }
        }
        binding.delete1.setOnClickListener{
            viewModel.removeLocation(0)
            viewModel.removeLocation(0)
            binding.wantedLocation1.visibility = View.GONE
            binding.locationBox.visibility = View.GONE
        }
        binding.delete2.setOnClickListener{
            viewModel.removeLocation(2)
            viewModel.removeLocation(2)
            binding.wantedLocation2.visibility = View.GONE
        }
        binding.delete3.setOnClickListener{
            viewModel.removeLocation(4)
            viewModel.removeLocation(4)
            binding.wantedLocation3.visibility = View.GONE
        }
    }

    private fun setNewList(resourceId: Int) {
        val localList = resources.getStringArray(resourceId).toList()
        adapter2.updateList(localList)
        binding.secondLocation.adapter = adapter2

    }
}
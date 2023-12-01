package com.example.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentHireDatePickerBinding
import com.example.nongglenonggle.databinding.FragmentSuggestDialogBinding
import com.example.nongglenonggle.presentation.viewModel.worker.WorkerHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SuggestDialogFragment : DialogFragment() {
    private var _binding : FragmentSuggestDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorkerHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuggestDialogBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirm.setOnClickListener{
            dismiss()
        }

        binding.cancel.setOnClickListener{
            lifecycleScope.launch {
                viewModel.alarmSuggestionCancel().collect(){result->
                    dismiss()
                    Toast.makeText(requireContext(),"채용제안을 취소했습니다",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val windowParams = window?.attributes

        val width = resources.displayMetrics.widthPixels * 0.9

        windowParams?.width = width.toInt()
        windowParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = windowParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
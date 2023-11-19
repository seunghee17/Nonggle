package com.example.nongglenonggle.presentation.view.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentHireDatePickerBinding
import com.example.nongglenonggle.databinding.FragmentSuggestDialogBinding

class SuggestDialogFragment : DialogFragment() {
    private var _binding : FragmentSuggestDialogBinding? = null
    private val binding get() = _binding!!
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
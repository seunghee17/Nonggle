package com.capstone.nongglenonggle.presentation.view.dialog

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentLogoutDialogBinding
import com.capstone.nongglenonggle.databinding.FragmentOutDirectionDialogBinding
import com.capstone.nongglenonggle.presentation.view.worker.resume.ResumeActivity

class OutDirectionDialogFragment : DialogFragment() {
    private var _binding : FragmentOutDirectionDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOutDirectionDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancel.setOnClickListener{
            dismiss()
        }
        binding.confirm.setOnClickListener{
            dismiss()
            (activity as? ResumeActivity)?.finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            val size = Point()
            val display = window?.windowManager?.defaultDisplay
            display?.getSize(size)
            val screenWidth = size.x - 2 * dpToPx(20, requireContext())

            window?.setLayout(screenWidth, WindowManager.LayoutParams.WRAP_CONTENT)
            window?.setGravity(Gravity.CENTER)
        }
    }

    private fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

}
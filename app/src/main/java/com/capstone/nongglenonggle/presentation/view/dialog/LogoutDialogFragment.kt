package com.capstone.nongglenonggle.presentation.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.capstone.nongglenonggle.databinding.FragmentSuggestDialogBinding
import com.capstone.nongglenonggle.presentation.view.FirstActivity
import com.google.android.material.internal.ViewUtils.dpToPx
import com.google.firebase.auth.FirebaseAuth

class LogoutDialogFragment : DialogFragment() {
    private var _binding : FragmentLogoutDialogBinding? = null
    private val binding get() = _binding!!
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogoutDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancel.setOnClickListener{
            dismiss()
        }
        binding.confirm.setOnClickListener{
            dismiss()
            FirebaseAuth.getInstance().signOut()
            activity?.finishAffinity()
            val intent = Intent(requireContext(), FirstActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
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
package com.capstone.nongglenonggle.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentSettingBinding
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.view.dialog.LogoutDialogFragment
import com.google.firebase.auth.FirebaseAuth

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.logoutBtn.setOnClickListener{
            showDialog()
        }
    }

    private fun showDialog(){
        val dialog = LogoutDialogFragment()
        dialog.show(parentFragmentManager,"logout")
    }

}
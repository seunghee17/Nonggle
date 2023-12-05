package com.capstone.nongglenonggle.presentation.view.signup

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.capstone.nongglenonggle.R

class Dialog2Fragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog2,null)
        val closebtn = dialogView.findViewById<ImageView>(R.id.closebtn)
        closebtn.setOnClickListener{
            dismiss()
        }
        return AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
    }

}
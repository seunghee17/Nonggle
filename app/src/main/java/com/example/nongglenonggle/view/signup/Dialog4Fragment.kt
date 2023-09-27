package com.example.nongglenonggle.view.signup

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.nongglenonggle.R
import java.lang.IllegalStateException


class Dialog4Fragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog4,null)
        val closebtn = dialogView.findViewById<ImageView>(R.id.closebtn)
        closebtn.setOnClickListener{
            dismiss()
        }
        return AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
    }
}
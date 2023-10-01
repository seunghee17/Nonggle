package com.example.nongglenonggle.presentation.viewModel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nongglenonggle.R

class FirstViewModel : ViewModel() {
    var isSelected = false
    private set

    fun setButtonSelected()
    {
        isSelected = !isSelected
    }

}
package com.capstone.nongglenonggle.presentation.viewModel

import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {
    var isSelected = false
    private set

    fun setButtonSelected()
    {
        isSelected = !isSelected
    }

}
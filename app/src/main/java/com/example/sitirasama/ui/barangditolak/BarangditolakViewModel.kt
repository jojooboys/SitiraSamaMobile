package com.example.sitirasama.ui.barangditolak

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BarangditolakViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}
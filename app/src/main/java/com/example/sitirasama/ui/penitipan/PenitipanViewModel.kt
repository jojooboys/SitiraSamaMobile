package com.example.sitirasama.ui.penitipan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PenitipanViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Penitipan Fragment"
    }
    val text: LiveData<String> = _text
}
package com.uasmobile.ceritakamu.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _name = MutableLiveData<String>().apply {
        value = "Brian"
    }
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>().apply {
        value = "brian@gmail.com"
    }
    val email: LiveData<String> = _email

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }
}

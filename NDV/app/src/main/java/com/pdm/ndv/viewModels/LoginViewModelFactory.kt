package com.pdm.ndv.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pdm.ndv.repositories.UserRepository
import java.lang.Exception

class LoginViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw Exception("Unknown ViewModel Class")
    }
}
package com.pdm.ndv.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pdm.ndv.R
import com.pdm.ndv.data.entities.UserAuth
import com.pdm.ndv.network.entities.UserEmailPwd
import com.pdm.ndv.network.entities.UserPwd
import com.pdm.ndv.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    var username = MutableLiveData("")
    var password = MutableLiveData("")
    var email = MutableLiveData("")
    var responseSuccess = MutableLiveData<Boolean>()
    var registerSuccess = MutableLiveData<Boolean>()
    private var _loading = MutableLiveData(View.GONE)
    val loading: LiveData<Int> get() = _loading
    private var _error = MutableLiveData<Int?>(null)
    val error: LiveData<Int?> get() = _error

    fun onSubmit() {
        _loading.value = View.VISIBLE
        _error.value = null
        viewModelScope.launch {
            try {
                if (username.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
                    _error.value = R.string.login_form_error
                } else {
                    val credenciales = UserPwd(username.value!!, password.value!!)
                    val call = repository.userSignIn(credenciales)

                    if (call.code() == 200) {
                        var usuario = UserAuth(1, call.body()!!.string(), credenciales.username)
                        repository.insertOrUpdateAuthUser(usuario)
                        responseSuccess.value = true
                    }
                    else {
                        responseSuccess.value = false
                    }
                }
            } catch (e: HttpException) {
                _error.value = R.string.sign_in_form_error
            } finally {
                _loading.value = View.GONE
            }
        }
    }

    fun onRegister() {
        _loading.value = View.VISIBLE
        _error.value = null
        viewModelScope.launch {
            try {
                if (username.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
                    _error.value = R.string.login_form_error
                } else {
                    val credenciales = UserEmailPwd(username.value!!, email.value!!, password.value!!)
                    val call = repository.userSignUp(credenciales)

                    if (call.code() == 201) {
                        val credencialesAlt = UserPwd(username.value!!, password.value!!)
                        val callAlt = repository.userSignIn(credencialesAlt)
                        var usuario = UserAuth(1, callAlt.body()!!.string(), credenciales.username)
                        repository.insertOrUpdateAuthUser(usuario)
                        registerSuccess.value = true
                    }
                    else {
                        registerSuccess.value = false
                    }
                }
            } catch (e: HttpException) {
                _error.value = R.string.sign_in_form_error
            } finally {
                _loading.value = View.GONE
            }
        }
    }

    suspend fun onLogOut() {
        var usuario = UserAuth(1, "","")
        repository.insertOrUpdateAuthUser(usuario)
        registerSuccess.value = false
        responseSuccess.value = false
    }

    suspend fun validateSignInToken(): Boolean {
        return repository.testAuth()
    }
}
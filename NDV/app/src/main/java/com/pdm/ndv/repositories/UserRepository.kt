package com.pdm.ndv.repositories

import android.util.Log
import com.pdm.ndv.data.dao.NDVDao
import com.pdm.ndv.data.entities.UserAuth
import com.pdm.ndv.network.NDVAPI
import com.pdm.ndv.network.entities.UserEmailPwd
import com.pdm.ndv.network.entities.UserPwd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class UserRepository(private val API: NDVAPI, private val nvdDao: NDVDao) {
    suspend fun userSignIn(credenciales: UserPwd) = withContext(Dispatchers.IO) {
        var respuesta = NDVAPI.service.signIn(credenciales)
        respuesta
    }

    suspend fun userSignUp(credenciales: UserEmailPwd) = withContext(Dispatchers.IO) {
        var respuesta = NDVAPI.service.signUp(credenciales)
        respuesta
    }

    suspend fun testAuth() = withContext(Dispatchers.IO) {
        var responseSuccess = false
        var token = ""
        try {
            token = nvdDao.getUserAuthById(1).token
        } catch (e: Exception) {
        }
        Log.i("INFO", token)
        if (!token.isNullOrEmpty()) {
            var json = JSONObject(token)
            token = "Bearer " + json.get("token")
            var respuesta = NDVAPI.service.testAuth(token)

            if (respuesta.code() == 200)
                responseSuccess = true
        }
        responseSuccess
    }

    suspend fun insertOrUpdateAuthUser(userAuth: UserAuth) = withContext(Dispatchers.IO) {
        nvdDao.insertOrUpdateAuthUser(userAuth)
    }

    suspend fun getUserAuthById(id: Int) = withContext(Dispatchers.IO) {
        nvdDao.getUserAuthById(id)
    }

    suspend fun deleteUserAuthById(id: Int) = withContext(Dispatchers.IO) {
        nvdDao.deleteUserAuthById(id)
    }
}
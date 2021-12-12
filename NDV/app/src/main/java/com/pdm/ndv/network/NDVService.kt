package com.pdm.ndv.network

import com.pdm.ndv.network.entities.UserPwd
import com.pdm.ndv.network.entities.UserEmailPwd
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface NDVService {
    @POST("auth/signup")
    suspend fun signUp(@Body credenciales: UserEmailPwd): retrofit2.Response<ResponseBody>

    @POST("auth/signin")
    suspend fun signIn(@Body credenciales: UserPwd): retrofit2.Response<ResponseBody>

    @GET("test")
    suspend fun testAuth(@Header("Authorization") token: String): retrofit2.Response<ResponseBody>
}
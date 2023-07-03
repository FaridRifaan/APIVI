package com.example.demoapp.User

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import javax.security.auth.callback.Callback

interface UserApi {
    @Multipart
    @POST("/predict")
    fun UserRequest(@Part img: MultipartBody.Part) : Call<ResponseBody>

}
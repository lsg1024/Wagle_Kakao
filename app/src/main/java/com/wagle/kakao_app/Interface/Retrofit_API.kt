package com.wagle.kakao_app.Interface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit_API {
    private val retrofit = Retrofit.Builder()
        .baseUrl("")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(AuthRetrofitInterface::class.java)

    fun getInstance(): AuthRetrofitInterface? {
        return api
    }
}
package com.wagle.kakao_app.Interface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {

    private val retrofit = Retrofit.Builder()
        .baseUrl("")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(AuthRetrofitInterface::class.java)

    fun getInstance(): AuthRetrofitInterface? {
        return api
    }
}


//    private val retrofitClient : Retrofit.Builder by lazy {
//        Retrofit.Builder()
//            .baseUrl("http://oceanit.synology.me:8001/")
//            .addConverterFactory(GsonConverterFactory.create())
//
//
//    }

//    val OPEN_SERVICE: AuthRetrofitInterface by lazy {
//        retrofitClient.build().create(AuthRetrofitInterface::class.java)!!
//    }
//}
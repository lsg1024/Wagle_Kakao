package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class WaglePickDTO(

    @SerializedName("result")
    val result : ArrayList<result>

)

data class result (

    @SerializedName("user_key")
    val user_key : String,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("img")
    val img : String,

    @SerializedName("temperature")
    val temperature : Int,

    @SerializedName("intro")
    val intro : String

)

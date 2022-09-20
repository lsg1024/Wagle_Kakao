package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyProfileDTO(

    @SerializedName("result")
    val MyProfile : ArrayList<MyProfile_data>
)

data class MyProfile_data(

    @SerializedName("img")
    val img : String,

    @SerializedName("nickname")
    val nickname : String,

    @SerializedName("mbti")
    val mbti : String,

    @SerializedName("intro")
    val intro : String,

    @SerializedName("tag")
    val tag : String,

    ) : Serializable

package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfoDTO(

    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("sex")
    val sex : String,
    @SerializedName("tags")
    val tag : String,
    @SerializedName("mbti")
    val mbti : String,
    @SerializedName("intro")
    val intro : String,
    @SerializedName("token")
    val token : String

) : Serializable

data class UserInfoResponse(
    @SerializedName("result")
    var result : String
)
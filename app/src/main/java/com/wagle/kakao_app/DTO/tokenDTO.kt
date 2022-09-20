package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class tokenDTO(
    @SerializedName("result")
    val tokenResult : List<tokenResponse>
)

data class tokenResponse(
    @SerializedName("login")
    val login : Boolean,
    @SerializedName("user_key")
    val user_key : Int,
)



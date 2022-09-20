package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class PostCreatePlace(
    @SerializedName("place")
    val place : String,
    @SerializedName("address")
    val address : String,
    @SerializedName("des")
    val des : String,
    @SerializedName("img")
    val img : String,
)
data class ResponsePlace(
    var result : String? = null
)





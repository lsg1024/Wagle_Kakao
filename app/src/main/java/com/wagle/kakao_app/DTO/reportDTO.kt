package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class reportDTO(
    @SerializedName("result")
    val result : String

    )


data class guesetBook_report(
    @SerializedName("user_key")
    val user_key : String,
    @SerializedName("Xuser_key")
    val Xuser_key : Int,
    @SerializedName("gb_key")
    val gb_key : Int,
    @SerializedName("des")
    val des : String
)

data class accompany_report(
    @SerializedName("user_key")
    val user_key : String,
    @SerializedName("Xuser_key")
    val Xuser_key : Int,
    @SerializedName("post_key")
    val gb_key : Int,
    @SerializedName("des")
    val des : String
)



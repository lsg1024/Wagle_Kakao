package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class WithGuestpParticipateDTO (

    @SerializedName("db_data")
    val db_data : each_key,

    @SerializedName("user_key")
    val user_key : String,

    @SerializedName("result")
    val result : String

)

data class each_key (

    @SerializedName("room_key")
    val room_key : Int,

    @SerializedName("post_key")
    val post_key : Int,

    @SerializedName("title")
    val title : String,

    @SerializedName("type")
    val type : Int

)
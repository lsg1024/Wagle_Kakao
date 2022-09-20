package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class ChatDTO(
    @SerializedName("db_data")
    val db_data : ArrayList<room_list>
)

data class room_list(

    @SerializedName("room_key")
    val room_key : Int,

    @SerializedName("title")
    val title : String,

    @SerializedName("nickname")
    val nickname : String,

    @SerializedName("img")
    val img : String
)

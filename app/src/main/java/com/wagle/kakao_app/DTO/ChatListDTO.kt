package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class ChatListDTO (

    @SerializedName("db_data")
    val chat_info : ArrayList<chat_list>

)

data class chat_list(

    @SerializedName("room_key")
    val room_key : Int,

    @SerializedName("user_key")
    val user_key : String,

    @SerializedName("post_key")
    val post_key : Int,

    @SerializedName("title")
    val title : String,

    @SerializedName("nickname")
    val nickname : String,

    @SerializedName("img")
    val img : String,

    @SerializedName("type")
    val type : Int

)

data class post_user_info (
    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("img")
    val img: String
)
package com.wagle.kakao_app.Chatting

data class joinData (
    val room_key : Int,
    val post_key : Int,
    val title : String,
    val type : Int,
    val user_key: String
)




data class RoomData (
    val room_key : Int,
    val user_key : String,
    val msg : String
)
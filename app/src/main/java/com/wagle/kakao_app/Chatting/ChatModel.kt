package com.wagle.kakao_app.Chatting


// type에 따른 ViewHolder 지정하기 위해 data class에 type 정의

class ChatModel(
    val chat_key : Int,
    val user_key : Int,
    val msg: String,
    val date: String,
    val nickname : String,
    val img: String,

    var type: Int
)

/* 내 채팅 : script(내용), date_time(보낸시간)
상대방 채팅 : profile_image, name, script, date_time */

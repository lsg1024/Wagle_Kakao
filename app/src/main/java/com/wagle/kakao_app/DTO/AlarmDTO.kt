package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class AlarmDTO (

    //알람 리슽트 보여주기
    @SerializedName("db_data")
    val alarm_db_data : ArrayList<alarm_db_data>

)

data class alarm_db_data(

    @SerializedName("user_key")
    val user_key : Int,

    @SerializedName("img")
    val img : String,

    @SerializedName("msg")
    val msg : String,

    @SerializedName("time")
    val time : String
)
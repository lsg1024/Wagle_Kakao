package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName


// 최근 업로드 글 리스트 페이지에서
data class WithRealtimeDTO (

//    // 시간, 참여인원
//    @SerializedName("post_key")
//    val post_key : Int,

    @SerializedName("db_data")
    val real_time_data : ArrayList<real_time_data>

)


data class real_time_data (
    @SerializedName("user_key")
    val user_key : Int,

    @SerializedName("post_key")
    val post_key: Int,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("img")
    val img : String,

    @SerializedName("title")
    val title: String,

    @SerializedName("personnel")
    val personnel : Int,

    @SerializedName("count_personnel")
    val count_personnel : Int,

    @SerializedName("date_update")
    val date_update : String
)


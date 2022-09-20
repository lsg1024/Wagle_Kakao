package com.wagle.finaltest.DTO

import com.google.gson.annotations.SerializedName


// 동행 main에서 사용

data class WithFragmentDTO (


    // 와글 pick! 친구 추천


    // 검색창


    // 실시간 글 보여주는
    @SerializedName("real_time_data")
    val main_real_time_data : ArrayList<main_real_time_data>,


    // 곧 마감되는 글 보여주는
    @SerializedName("closing_data")
    val main_closing_data : ArrayList<main_closing_data>

)

data class main_real_time_data(

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

data class main_closing_data(

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





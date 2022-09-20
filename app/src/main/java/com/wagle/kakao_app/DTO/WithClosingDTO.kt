package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class WithClosingDTO (
@SerializedName("db_data")
val closing_data : ArrayList<closing_data>,

)


data class closing_data(

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
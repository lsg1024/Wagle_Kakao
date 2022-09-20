package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName
import retrofit2.http.POST

data class PairListDTO(
    @SerializedName("db_data")
    val db_data : ArrayList<pair_list>,

    @SerializedName("host_key")
    val host_key : Int,

    @SerializedName("post_key")
    val post_key: Int
)

data class pair_list(

    @SerializedName("user_key")
    val user_key : Int,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("img")
    val img : String,

    @SerializedName("personnel")
    val personnel : Int?,

    @SerializedName("connect")
    val connect : Int,
)

data class user_data(

    @SerializedName("user_key")
    val user_key : Int,

    @SerializedName("post_key")
    val post_key : Int
)

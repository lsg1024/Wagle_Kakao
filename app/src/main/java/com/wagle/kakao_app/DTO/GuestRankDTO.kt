package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class GuestRankDTO(

    @SerializedName("result")
    val result: ArrayList<Responeresult>,

)

data class Responeresult(
    @SerializedName("gp_key")
    val gp_key: Int,
    @SerializedName("place")
    val place: String,
    @SerializedName("heart")
    val heart: Int,
    @SerializedName("img")
    val img : String,
    @SerializedName("address")
    val address : String
)


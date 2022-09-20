package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class search_withDTO(
    @SerializedName("db_data")
    val with_searchResult : ArrayList<WithResponse>
)

data class WithResponse(

    @SerializedName("nickname")
    val nickname : String,

    @SerializedName("user_key")
    val user_key : Int,

    @SerializedName("img")
    val img: String,
)

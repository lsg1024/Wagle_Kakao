package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResultDTO(

    @SerializedName("result")
    val searchResult : ArrayList<SearchResponse>
)

data class SearchResponse(

    @SerializedName("comment")
    val comment : String,
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("gb_key")
    val gb_key: Int,
    @SerializedName("gp_key")
    val gp_key: Int,
    @SerializedName("place")
    val place: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("heart")
    val heart : Int,
    @SerializedName("user_key")
    val user_key : Int


) : Serializable

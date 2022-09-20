package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class user_placeDTO(

    @SerializedName("result")
    val readResult : ArrayList<ReadResponse>
)

data class ReadResponse(

    @SerializedName("gb_key")
    val gb_key : Int,
    @SerializedName("img")
    val img : String,
    @SerializedName("comment")
    val comment : String,
    @SerializedName("gp_key")
    val gp_key : Int,
    @SerializedName("place")
    val place : String,
    @SerializedName("address")
    val address : String,
    @SerializedName("user_key")
    val user_key : Int,
    @SerializedName("user_img")
    val user_img : String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("heart_user")
    val heart_user : String,
    @SerializedName("heart")
    val heart : Int

) : Serializable

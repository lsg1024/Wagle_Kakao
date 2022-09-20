package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class PostDetailDTO(
    @SerializedName("db_data")
    val db_data : ArrayList<db_data_response>,

    @SerializedName("user_key")
    val user_key : ArrayList<user_key_response>,

    @SerializedName("user_keys")
    val user_keys : ArrayList<user_key_response>,

    @SerializedName("post_key")
    val post_key : String
)

data class db_data_response(
    @SerializedName("nickname")
    val nickname : String,

    @SerializedName("img")
    val img : String,

    @SerializedName("title")
    val title : String,

    @SerializedName("des")
    val des : String,

    @SerializedName("personnel")
    val personnel : Int

//    tag, date_upload, date_update
)

data class user_key_response(
    @SerializedName("user_key")
    val user_key : Int
)

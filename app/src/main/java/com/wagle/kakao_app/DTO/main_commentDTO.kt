package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class main_comment(

    @SerializedName("gp_key")
    val gp_key : Int,
    @SerializedName("comment")
    val des : String,
    @SerializedName("img")
    val img : String,
)

data class comment_response(
    var result : String?= null
)

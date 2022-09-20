package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class userHeartDTO(
    @SerializedName("gp_key")
    val gp_key : Int
)

data class PostUserHeart(
    var result : String? = null
)

data class commentHeartDTO(
    @SerializedName("gb_key")
    val gb_key : Int
)

data class CommentHeart(
    var result : String? = null
)


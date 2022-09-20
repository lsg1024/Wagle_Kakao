package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class WithPostCreateDTO (
//


    @SerializedName("title")
    val title : String,

    @SerializedName("des")
    val des : String,

    @SerializedName("tags")
    val tags : String,

    @SerializedName("personnel")
    val personnel : String

)

data class PostResult(
    var user_key : String,
    var post_key : Int
)
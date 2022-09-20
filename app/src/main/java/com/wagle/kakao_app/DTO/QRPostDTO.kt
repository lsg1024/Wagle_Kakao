package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data  class QRPostDTO (
    @SerializedName("result")
    val result : String
)
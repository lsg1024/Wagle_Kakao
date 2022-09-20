package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SaveDecoIndexDTO(

    @SerializedName("deco_key")
    val deco_key : Int,
    @SerializedName("index")
    val index : Int

) : Serializable

data class SaveDecoResponse(
    var result : String? = null
)
package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DecorationDTO(

    @SerializedName("db_data")
    val db_data: ArrayList<DecorationResponse>
)

data class DecorationResponse(

    @SerializedName("deco_key")
    val deco_key : Int

) : Serializable
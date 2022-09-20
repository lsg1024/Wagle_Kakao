package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShowMyDecoDTO(

    @SerializedName("db_data")
    val db_data : ArrayList<data_list>

) : Serializable

data class data_list(

    @SerializedName("deco_key")
    val deco_key : Int,
    @SerializedName("deco_index")
    val deco_index : Int
)
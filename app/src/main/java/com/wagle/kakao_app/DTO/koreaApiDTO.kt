package com.wagle.kakao_app.DTO

import com.google.gson.annotations.SerializedName

data class koreaApi(val response: HBResponse)

data class HBResponse(
    @SerializedName("header")
    val header : HB_Header,
    @SerializedName("body")
    val body : HB_Body
)

data class HB_Header(
    @SerializedName("resultCode")
    val resultCode : String?,
    @SerializedName("resultMsg")
    val resultMsg : String?
)

data class HB_Body(
    @SerializedName("items")
    val items : item
)

data class item(
    @SerializedName("item")
    val item : ArrayList<total_Data>
)

data class total_Data(
    @SerializedName("addr1")
    val addr1 : String?,
    @SerializedName("addr2")
    val addr2 : String?,
    @SerializedName("firstimage")
    val firstimage : String?,
    @SerializedName("mapx")
    val mapx : String?,
    @SerializedName("mapy")
    val mapy : String?,
    @SerializedName("title")
    val title : String?
)

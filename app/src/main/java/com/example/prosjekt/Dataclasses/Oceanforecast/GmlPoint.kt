package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class GmlPoint(
    @SerializedName("gml:id")
    val gmlId: String,
    @SerializedName("gml:pos")
    val gmlPos: String,
    val srsName: String
)
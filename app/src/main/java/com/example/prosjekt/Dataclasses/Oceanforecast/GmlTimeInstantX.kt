package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class GmlTimeInstantX(
    @SerializedName("gml:id")
    val gmlId: String,
    @SerializedName("gml:timePosition")
    val gmlTimePosition: String
)
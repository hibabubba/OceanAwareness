package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class GmlTimePeriod(
    @SerializedName("gml:begin")
    val gmlBegin: String,
    @SerializedName("gml:end")
    val gmlEnd: String,
    @SerializedName("gml:id")
    val gmlId: String
)
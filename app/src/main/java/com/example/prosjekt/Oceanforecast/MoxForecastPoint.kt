package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class MoxForecastPoint(
    @SerializedName("gml:Point")
    val gmlPoint: GmlPoint
)
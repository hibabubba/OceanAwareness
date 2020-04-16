package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class MoxValidTime(
    @SerializedName("gml:TimePeriod")
    val gmlTimePeriod: GmlTimePeriod
)
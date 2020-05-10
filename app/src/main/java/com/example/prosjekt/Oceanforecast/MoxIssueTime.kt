package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class MoxIssueTime(
    @SerializedName("gml:TimeInstant")
    val gmlTimeInstant: GmlTimeInstant
)
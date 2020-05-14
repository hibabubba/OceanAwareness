package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class MoxNextIssueTime(
    @SerializedName("gml:TimeInstant")
    val gmlTimeInstant: GmlTimeInstantX
)
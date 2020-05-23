package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class Oceanforecast(
    @SerializedName("mox:forecast")
    val moxForecast: List<MoxForecast>,
    @SerializedName("xmlns:gml")
    val xmlnsGml: String,
    @SerializedName("xmlns:metno")
    val xmlnsMetno: String,
    @SerializedName("xmlns:mox")
    val xmlnsMox: String,
    @SerializedName("xmlns:xlink")
    val xmlnsXlink: String,
    @SerializedName("xsi:schemaLocation")
    val xsiSchemaLocation: String
)
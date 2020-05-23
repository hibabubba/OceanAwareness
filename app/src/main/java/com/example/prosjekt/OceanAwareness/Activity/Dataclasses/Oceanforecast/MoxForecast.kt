package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class MoxForecast(
    @SerializedName("metno:OceanForecast")
    val metnoOceanForecast: MetnoOceanForecast
)
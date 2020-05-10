package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class MetnoOceanForecast(
    @SerializedName("gml:id")
    val gmlId: String,
    @SerializedName("mox:meanTotalWaveDirection")
    val moxMeanTotalWaveDirection: MoxMeanTotalWaveDirection,
    @SerializedName("mox:seaBottomTopography")
    val moxSeaBottomTopography: MoxSeaBottomTopography,
    @SerializedName("mox:seaCurrentDirection")
    val moxSeaCurrentDirection: MoxSeaCurrentDirection,
    @SerializedName("mox:seaCurrentSpeed")
    val moxSeaCurrentSpeed: MoxSeaCurrentSpeed,
    @SerializedName("mox:seaIcePresence")
    val moxSeaIcePresence: MoxSeaIcePresence,
    @SerializedName("mox:seaTemperature")
    val moxSeaTemperature: MoxSeaTemperature,
    @SerializedName("mox:significantTotalWaveHeight")
    val moxSignificantTotalWaveHeight: MoxSignificantTotalWaveHeight,
    @SerializedName("mox:validTime")
    val moxValidTime: MoxValidTime
)
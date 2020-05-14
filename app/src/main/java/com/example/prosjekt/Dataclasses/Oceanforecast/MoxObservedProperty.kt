package com.example.prosjekt.Oceanforecast


import com.google.gson.annotations.SerializedName

data class MoxObservedProperty(
    @SerializedName("xlink:href")
    val xlinkHref: String
)
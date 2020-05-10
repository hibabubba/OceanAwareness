package com.example.prosjekt.Locationforecast


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("class")
    val classX: String,
    val time: List<Time>
)
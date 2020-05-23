package com.example.prosjekt.Locationforecast


data class Location(
    val cloudiness: Cloudiness,
    val fog: Fog,
    val humidity: Humidity,
    val latitude: String,
    val longitude: String,
    val precipitation: Precipitation,
    val temperature: Temperature,
    val windDirection: WindDirection,
    val windSpeed: WindSpeed
)
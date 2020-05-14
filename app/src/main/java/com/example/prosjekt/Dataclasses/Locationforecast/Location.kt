package com.example.prosjekt.Locationforecast


data class Location(
    val altitude: String,
    val cloudiness: Cloudiness,
    val dewpointTemperature: DewpointTemperature,
    val fog: Fog,
    val highClouds: HighClouds,
    val humidity: Humidity,
    val latitude: String,
    val longitude: String,
    val lowClouds: LowClouds,
    val maxTemperature: MaxTemperature,
    val mediumClouds: MediumClouds,
    val minTemperature: MinTemperature,
    val precipitation: Precipitation,
    val pressure: Pressure,
    val symbol: Symbol,
    val symbolProbability: SymbolProbability,
    val temperature: Temperature,
    val temperatureProbability: TemperatureProbability,
    val windDirection: WindDirection,
    val windGust: WindGust,
    val windProbability: WindProbability,
    val windSpeed: WindSpeed
)
package com.example.prosjekt.RSS

data class Info(
    val __text: String,
    val area: Area,
    val category: String,
    val certainty: String,
    val contact: String,
    val description: String,
    val effective: String,
    val event: String,
    val eventCode: EventCode,
    val expires: String,
    val headline: String,
    val instruction: String,
    val language: String,
    val onset: String,
    val parameter: List<Parameter>,
    val responseType: String,
    val senderName: String,
    val severity: String,
    val urgency: String,
    val web: String
)
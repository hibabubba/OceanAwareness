package com.example.prosjekt.RSS

data class Alert(
    val __text: String,
    val _xmlns: String,
    val code: String,
    val identifier: String,
    val incidents: String,
    val info: List<Info>,
    val msgType: String,
    val scope: String,
    val sender: String,
    val sent: String,
    val status: String
)
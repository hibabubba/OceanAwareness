package com.example.prosjekt.RSS

data class Item (
    val title:String,
    val pubDate:String,
    val link:String,
    val guid:String,
    val thumbnail:String,
    val description:String,
    val content:String
)
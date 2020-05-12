package com.example.prosjekt.RSS

data class RSSObject (
    val status:String,
    val feed:Feed,
    val items:List<Item>
)
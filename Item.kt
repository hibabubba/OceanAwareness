package com.example.prosjektin2000

data class Item(val title:String, val pubDate :String, val link:String,val guide:String,
                val author:String, val thumbnail:String, val description:String, val content:String,
                val enclosure:Object,val categories:List<String>)
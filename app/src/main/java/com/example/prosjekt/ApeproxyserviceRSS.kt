package com.example.prosjekt.RSS

import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.Oceanforecast.Oceanforecast
import com.example.prosjekt.RSS.RSSObject
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import retrofit2.Call

public interface ApiproxyserviceRSS {

    @GET("weatherapi/metalerts/1.1/.json")
    fun getRSSfeed(
        @Query("lat") Latitude: Double,
        @Query("lon") Longitude: Double
    ): Call<RSSObject>



    companion object{
        fun create() : ApiproxyserviceRSS {
            val gson = GsonBuilder().create()

            val okHttpclient = OkHttpClient.Builder().build()

            val retro = Retrofit.Builder().client(okHttpclient).baseUrl("https://api.rss2json.com/v1/api.json?rss_url=https://in2000-apiproxy.ifi.uio.no/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retro.create(ApiproxyserviceRSS::class.java)
        }

    }

}
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
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Url

public interface ApiproxyserviceRSS {

    @GET("api.json?rss_url=https://in2000-apiproxy.ifi.uio.no/weatherapi/metalerts/1.1/")
    //https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fin2000-apiproxy.ifi.uio.no%2Fweatherapi%2Fmetalerts%2F1.1%2F%3Flat%3D10.09899%26lon%3D30.970696
    fun getRSSfeedAsync(
        //@Query("rss_url") Link : String,
        @Query("lat") Latitude: Double,
        @Query("lon") Longitude: Double
    ): Deferred<RSSObject>



    companion object{
        fun invoke() : ApiproxyserviceRSS {
            val gson = GsonBuilder().create()

            val okHttpclient = OkHttpClient.Builder().build()

            val retro = Retrofit.Builder().client(okHttpclient)
                .baseUrl("https://api.rss2json.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retro.create(ApiproxyserviceRSS::class.java)
        }

    }
 //https://api.rss2json.com/v1/api.json?rss_url=https://in2000-apiproxy.ifi.uio.no/weatherapi/metalerts/1.1/"
}
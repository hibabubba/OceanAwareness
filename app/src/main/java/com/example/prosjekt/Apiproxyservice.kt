package com.example.prosjekt

import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.Oceanforecast.Oceanforecast
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import retrofit2.Call

public interface Apiproxyservice {

    @GET("weatherapi/locationforecast/1.9/.json")
    fun getLocationforecast(
        @Query("lat") Latitude: Double,
        @Query("lon") Longitude: Double
    ): Call<Locationforecast>

    @GET("weatherapi/oceanforecast/0.9/.json")
    fun getOceanforecast(
        @Query("lat") Latitude: Double,
        @Query("lon") Longitude: Double
    ): Call<Oceanforecast>

        companion object{
            fun create(): Apiproxyservice {
                val gson = GsonBuilder().create()

                val okHttpclient= OkHttpClient.Builder().build()

               val retro=  Retrofit.Builder().client(okHttpclient).baseUrl("https://in2000-apiproxy.ifi.uio.no/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

                    return retro.create(Apiproxyservice::class.java)
            }

        }

    }


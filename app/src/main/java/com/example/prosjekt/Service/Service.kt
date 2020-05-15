package com.example.prosjekt.Service

import com.example.prosjekt.Apiproxyservice
import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.Oceanforecast.Oceanforecast
import com.example.prosjekt.RSS.ApiproxyserviceRSS
import com.example.prosjekt.RSS.RSSObject
import javax.security.auth.callback.Callback

class Service {

    private val  apiService = Apiproxyservice.create()



    fun getLocationForecast(latitude : Double, longitude : Double) : Locationforecast? {

        lateinit var result : Locationforecast

        val call = apiService.getLocationforecast(latitude, longitude)
        call.enqueue(object : retrofit2.Callback<Locationforecast> {
            override fun onFailure(call: retrofit2.Call<Locationforecast>, t: Throwable) {
                println("FAILET IGJEN")
            }

            override fun onResponse (
                call: retrofit2.Call<Locationforecast>,
                response: retrofit2.Response<Locationforecast>
            ) {
                if (response.isSuccessful) {
                    result = response.body() as Locationforecast
                }
            }
        })

        return result
    }



    fun getOceanForecast(latitude: Double, longitude: Double) : Oceanforecast {
        lateinit var result : Oceanforecast

        val call = apiService.getOceanforecast(latitude, longitude)
        call.enqueue(object : retrofit2.Callback<Oceanforecast> {
            override fun onFailure(call: retrofit2.Call<Oceanforecast>, t: Throwable) {
                println("FAILET IGJEN")
            }

            override fun onResponse (
                call: retrofit2.Call<Oceanforecast>,
                response: retrofit2.Response<Oceanforecast>
            ) {
                if (response.isSuccessful) {
                    result = response.body() as Oceanforecast
                }
            }
        })

        return result
    }




    fun getRSSFeed(latitude: Double, longitude: Double) : RSSObject? {

        println("long, service: $longitude")
        println("lat, service: $latitude")

        var RSSresult : RSSObject? = null

        val  apiService = ApiproxyserviceRSS.create()

        val call = apiService.getRSSfeedAsync(latitude, longitude)

        /*call.enqueue(object : retrofit2.Callback<RSSObject> {
            override fun onFailure(call: retrofit2.Call<RSSObject>, t: Throwable) {
            }

            override fun onResponse(
                call: retrofit2.Call<RSSObject>,
                response: retrofit2.Response<RSSObject>
            ) {
                if (response.isSuccessful) {
                    RSSresult = response.body() as RSSObject
                    println("service")
                    println(RSSresult)
                    callback.onSu



                }
            }
        })*/

        return RSSresult

    }
}
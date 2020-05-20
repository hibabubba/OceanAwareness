package com.example.prosjekt.Repository

import androidx.lifecycle.MutableLiveData
import com.example.prosjekt.Apiproxyservice
import com.example.prosjekt.Locationforecast.Locationforecast

class LocationRepository() {

    private val  apiService = Apiproxyservice.create()

    val liveDataForecast = MutableLiveData<Locationforecast>()

    suspend fun fetchForecast(lat : Double, long : Double) {
        println("INNElocation")

        var call = apiService.getLocationforecast(lat, long)
        call.enqueue(object : retrofit2.Callback<Locationforecast> {
            override fun onFailure(call: retrofit2.Call<Locationforecast>, t: Throwable) {
                println("FAILET IGJEN")
            }

            override fun onResponse(
                call: retrofit2.Call<Locationforecast>,
                response: retrofit2.Response<Locationforecast>
            ) {
                if (response.isSuccessful) { //
                    liveDataForecast.value = response.body()
                }
            }
        })
    }
}
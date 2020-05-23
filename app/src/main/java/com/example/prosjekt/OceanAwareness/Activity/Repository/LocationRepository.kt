package com.example.prosjekt.OceanAwareness.Activity.Repository

import androidx.lifecycle.MutableLiveData
import com.example.prosjekt.OceanAwareness.Activity.Dataclasses.Apiproxyservice
import com.example.prosjekt.Locationforecast.Locationforecast

class LocationRepository{

    private val  apiService = Apiproxyservice.create()
    val liveDataForecast = MutableLiveData<Locationforecast>()

    fun fetchForecast(lat : Double, long : Double) {


        val call = apiService.getLocationforecast(lat, long)
        call.enqueue(object : retrofit2.Callback<Locationforecast> {
            override fun onFailure(call: retrofit2.Call<Locationforecast>, t: Throwable) {
                println("Feil i LocationCall ")
            }

            override fun onResponse(
                call: retrofit2.Call<Locationforecast>,
                response: retrofit2.Response<Locationforecast>
            ) {
                if (response.isSuccessful) {
                    liveDataForecast.value = response.body()
                }
            }
        })
    }
}
package com.example.prosjekt.OceanAwareness.Activity.Repository

import androidx.lifecycle.MutableLiveData
import com.example.prosjekt.OceanAwareness.Activity.Dataclasses.Apiproxyservice
import com.example.prosjekt.Oceanforecast.Oceanforecast

class OceanRepository {
    private val  apiService = Apiproxyservice.create()

    val liveDataForecast = MutableLiveData<Oceanforecast>()

    fun fetchForecast(lat : Double, long : Double) {

        val call = apiService.getOceanforecast(lat, long)
        call.enqueue(object : retrofit2.Callback<Oceanforecast> {
            override fun onFailure(call: retrofit2.Call<Oceanforecast>, t: Throwable) {
                println("Feil i OceanCall")
            }

            override fun onResponse(
                call: retrofit2.Call<Oceanforecast>,
                response: retrofit2.Response<Oceanforecast>
            ) {
                if (response.isSuccessful) {
                    liveDataForecast.value = response.body()
                }
            }
        })
    }
}
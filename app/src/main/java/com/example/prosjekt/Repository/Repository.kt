package com.example.prosjekt.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.Oceanforecast.Oceanforecast
import com.example.prosjekt.RSS.ApiproxyserviceRSS
import com.example.prosjekt.RSS.RSSObject
import com.example.prosjekt.Service.Service
import kotlinx.coroutines.Dispatchers.IO

class Repository(private val service: Service) {
    //val apiRSS = ApiproxyserviceRSS.create()

    val liveDataRSS = MutableLiveData<RSSObject>()

    fun getRSSFeed(latitude: Double, longitude: Double) {

        println("long, service: $longitude")
        println("lat, service: $latitude")

        val  apiService = ApiproxyserviceRSS.create()

        val call = apiService.getRSSfeedAsync(latitude, longitude)

        call.enqueue(object : retrofit2.Callback<RSSObject> {
            override fun onFailure(call: retrofit2.Call<RSSObject>, t: Throwable) {
            }

            override fun onResponse(
                call: retrofit2.Call<RSSObject>,
                response: retrofit2.Response<RSSObject>
            ) {
                if (response.isSuccessful) {
                    println("service")
                    liveDataRSS.value = response.body()


                }
            }
        })

    }




















   /* private val liveDataLocation = MutableLiveData<Locationforecast>()

    private val liveDataOcean = MutableLiveData<Oceanforecast>()

    //val liveDataRSS = MutableLiveData<RSSObject>()

    fun getLocationforecast(lat : Double, long : Double) { liveDataLocation.value = service.getLocationForecast(lat, long) }

    fun getOceanforecast(lat : Double, long : Double) { liveDataOcean.value = service.getOceanForecast(lat, long) }

    //fun getRSSFeed(lat : Double, long : Double) : LiveData<RSSObject> {
    RSS = liveData(IO) {
        val forecast = getForecast(lat, long)
            emit(forecast)
        }

    }

    private suspend fun getForecast(lat : Double, long : Double) : RSSObject{
        return apiRSS.getRSSfeedAsync(lat, long).await()
    }*/




}
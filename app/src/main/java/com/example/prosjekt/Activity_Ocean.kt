package com.example.prosjekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.Oceanforecast.Oceanforecast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback

class Activity_Ocean : AppCompatActivity() {

    private val  apiService = Apiproxyservice.create()
    private var data: Oceanforecast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__ocean)
        CoroutineScope(Dispatchers.IO).launch {
            fetchJson()}
    }



    private suspend fun fetchJson() {

        val latitude = intent.getDoubleExtra("lati", 2000.00)

        val  longitude  = intent.getDoubleExtra("longi", 2000.00)



        var call = apiService.getOceanforecast(latitude, longitude)
            call.enqueue(object : retrofit2.Callback<Oceanforecast> {
                override fun onFailure(call: retrofit2.Call<Oceanforecast>, t: Throwable) {
                    println("FAILET IGJEN")
                }

                override fun onResponse(
                    call: retrofit2.Call<Oceanforecast>,
                    response: retrofit2.Response<Oceanforecast>
                ) {
                    if (response.isSuccessful) { //
                        data = response.body()
                         create_activity()
                    }
                }
            })

    }
 fun create_activity(){

     var icepresence : TextView = findViewById(R.id.icepresence)
     var temp: TextView = findViewById(R.id.seatemp)
     var seacurrentspeed: TextView = findViewById(R.id.seacurrentspeed)
     var seacurrentdirection: TextView = findViewById(R.id.seacurrentdir)
     var totalwavedirection: TextView = findViewById(R.id.totwavedir)
     var waveheight : TextView = findViewById(R.id.waveheight)
   //  var slider : SeekBar = findViewById(R.id.seekBar)


     icepresence.text = "Icepresence: \n" +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaIcePresence?.content + data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaIcePresence?.uom

     totalwavedirection.text = " Bølgeretning: \n" +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxMeanTotalWaveDirection?.content + "°"

     seacurrentdirection.text = " havstrømretning: \n"+
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaCurrentDirection?.content + "°"

     waveheight.text = " Bølgehøyde: \n" +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.content + data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.uom

     temp.text = " Havtemperatur: \n" +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaTemperature?.content+ "°C"
     seacurrentspeed.text = " Havstrømfart: \n" +
     data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaCurrentSpeed?.content + data?.moxForecast?.get(0)?.metnoOceanForecast?.moxSeaCurrentSpeed?.uom

 }



}



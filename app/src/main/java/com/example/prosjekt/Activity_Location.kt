package com.example.prosjekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


import com.example.prosjekt.Locationforecast.*


class Activity_Location : AppCompatActivity() {

   private val  apiService = Apiproxyservice.create()
   private var data: Locationforecast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__location)

        CoroutineScope(Dispatchers.IO).launch {
           fetchJson()}
   }



 private suspend fun fetchJson() {
            println("INNE")
              val latitude = intent.getDoubleExtra("lati", 2000.00)

             val  longitude  = intent.getDoubleExtra("longi", 2000.00)




        // val latitude = 59.429317
      //   val longitude= 10.545646

        var call = apiService.getLocationforecast(latitude, longitude)
        call.enqueue(object : retrofit2.Callback<Locationforecast> {
            override fun onFailure(call: retrofit2.Call<Locationforecast>, t: Throwable) {
                println("FAILET IGJEN")
            }

            override fun onResponse(
                call: retrofit2.Call<Locationforecast>,
                response: retrofit2.Response<Locationforecast>
            ) {
                if (response.isSuccessful) { //
                    data = response.body()

                    create_activity()


                }
            }
        })//}



 }

    fun create_activity(){
        var hoved : TextView= findViewById(R.id.hovedtext)
        var cloud: TextView = findViewById(R.id.cloud)
        var fog: TextView = findViewById(R.id.fog)
        var humid: TextView = findViewById(R.id.humid)
        var wind: TextView = findViewById(R.id.wind)
        var windDir: TextView = findViewById(R.id.winddicetion)
        var windSpeed : TextView= findViewById(R.id.windspeed)
       // var slider : SeekBar = findViewById(R.id.seekBar)

        // for( nr in 0..144){ FOR Å BRUKE TIL SEEKBAR
          //  if(nr %3 == 0){
                hoved.text=  data?.product?.time?.get(0)?.location?.temperature?.value+ "°C"
                cloud.text = " skyer: " + data?.product?.time?.get(0)?.location?.cloudiness?.percent
                fog.text = "Tåke: \n" + data?.product?.time?.get(0)?.location?.fog?.percent
                humid.text = " humiditet: \n" + data?.product?.time?.get(0)?.location?.humidity?.value
                wind.text = "vind: \n" +  data?.product?.time?.get(0)?.location?.windProbability?.value
                windDir.text = "navn: "+ data?.product?.time?.get(0)?.location?.windDirection?.name + "\n, Grader:"+ data?.product?.time?.get(0)?.location?.windDirection?.deg
                windSpeed.text = "vindstyrke: \n" + data?.product?.time?.get(0)?.location?.windSpeed?.mps

        //   }
       // }


    }
}

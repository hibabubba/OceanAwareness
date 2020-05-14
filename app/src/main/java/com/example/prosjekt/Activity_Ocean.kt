package com.example.prosjekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
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


    lateinit var icepresence : TextView
    lateinit var temp: TextView
    lateinit var seacurrentspeed: TextView
    lateinit var seacurrentdirection: TextView
    lateinit var totalwavedirection: TextView
    lateinit var waveheight : TextView
    lateinit var slider : SeekBar
    lateinit var time: TextView
    lateinit var location : TextView
    lateinit var arrow: ImageButton
    lateinit var mapbutton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__ocean)
        CoroutineScope(Dispatchers.IO).launch {
            fetchJson()}
    }



    private suspend fun fetchJson() {

        val latitude = intent.getDoubleExtra("lati", 2000.00)

        val  longitude  = intent.getDoubleExtra("longi", 2000.00)
        //SEtte lokasjon til activiteten
        location = findViewById(R.id.location)
        location.text = "KOORDINATER: \n" + "Latitude = "+ latitude + "\nLongitude = "+ longitude
        //SEtte bakgrunn
        var bakgrunn: ImageView = findViewById(R.id.bakgrunn)
            bakgrunn.setImageResource(R.drawable.dagbakgrunn)
            bakgrunn.setAdjustViewBounds(true);
            bakgrunn.setScaleType(ImageView.ScaleType.FIT_XY)



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
                        //lag startsiden når du åpner opp
                         create_activity()

                        //setter igjang seekbaren
                        set_seekbar()
                    }
                }
            })

    }
 fun create_activity(){

     icepresence  = findViewById(R.id.icepresence)
     temp = findViewById(R.id.seatemp)
     seacurrentspeed= findViewById(R.id.seacurrentspeed)
     seacurrentdirection = findViewById(R.id.seacurrentdir)
     totalwavedirection = findViewById(R.id.totwavedir)
     waveheight = findViewById(R.id.waveheight)
     slider  = findViewById(R.id.seekBar)
     time = findViewById(R.id.klokkelsett)
     arrow = findViewById(R.id.arrow)
     mapbutton = findViewById(R.id.mapbutton)



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



     //Sette arrow og mapbutton til clicklistener
     arrow.setOnClickListener {
         startActivity(Intent(this, MainActivity::class.java))
     }
     mapbutton.setOnClickListener {
         startActivity(Intent(this, MainActivity::class.java))
     }

 }

 fun set_seekbar(){


         this.slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
             override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    get_info(progress)
             }

             override fun onStartTrackingTouch(seekBar: SeekBar) {
             }

             override fun onStopTrackingTouch(seekBar: SeekBar) {
             }
         })

 }


fun get_info( nr:Int) {
    icepresence.text = "Icepresence: \n" +
            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaIcePresence?.content + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaIcePresence?.uom

    totalwavedirection.text = " Bølgeretning: \n" +
            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxMeanTotalWaveDirection?.content + "°"

    seacurrentdirection.text = " havstrømretning: \n"+
            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaCurrentDirection?.content + "°"

    waveheight.text = " Bølgehøyde: \n" +
            data?.moxForecast?.get(nr
            )?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.content + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSignificantTotalWaveHeight?.uom

    temp.text = " Havtemperatur: \n" +
            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaTemperature?.content+ "°C"
    seacurrentspeed.text = " Havstrømfart: \n" +
            data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaCurrentSpeed?.content + data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxSeaCurrentSpeed?.uom

    val tid =  data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin
    val tiden = tid!!.split("T" ,":")
    time.text = "KL: " + tiden[1]
}


}



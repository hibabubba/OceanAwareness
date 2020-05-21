package com.example.prosjekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.prosjekt.Locationforecast.Locationforecast
import com.example.prosjekt.Oceanforecast.Oceanforecast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback

class OceanActivity : AppCompatActivity() {

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
    private var longitude = 0.toDouble()
    private var latitude = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__ocean)
        CoroutineScope(Dispatchers.IO).launch {
            fetchJson()}
    }



    private suspend fun fetchJson() {

        latitude = intent.getDoubleExtra("lati", 2000.00)

        longitude = intent.getDoubleExtra("longi", 2000.00)
        //SEtte lokasjon til activiteten
        location = findViewById(R.id.location)
        location.text = "LOKASJON: \n" + "Latitude = "+ latitude + "\n Longitude = "+ longitude
        //SEtte bakgrunn
        var bakgrunn: ImageView = findViewById(R.id.bakgrunn)
            bakgrunn.setImageResource(R.drawable.ocean)
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

                        //setter igjang scrollviewt
                        create_scrollview()
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
         val intent = Intent(this, MenuActivity::class.java)
         intent.putExtra("lati", latitude)
         intent.putExtra("longi", longitude)
         startActivity(intent)
     }
     mapbutton.setOnClickListener {
         startActivity(Intent(this, MainActivity::class.java))
     }

 }
    fun create_scrollview(){
        //hente alle 48 buttonene
        var liste =  listOf<Button>(findViewById(R.id.id1), findViewById(R.id.id2), findViewById(R.id.id3), findViewById(R.id.id4), findViewById(R.id.id5),
            findViewById(R.id.id6), findViewById(R.id.id7), findViewById(R.id.id8),findViewById(R.id.id9),findViewById(R.id.id10), findViewById(R.id.id11),
            findViewById(R.id.id12), findViewById(R.id.id13), findViewById(R.id.id14), findViewById(R.id.id15), findViewById(R.id.id16), findViewById(R.id.id17),
            findViewById(R.id.id18), findViewById(R.id.id19), findViewById(R.id.id20), findViewById(R.id.id21), findViewById(R.id.id22) , findViewById(R.id.id23),
            findViewById(R.id.id24),findViewById(R.id.id25), findViewById(R.id.id26),findViewById(R.id.id27), findViewById(R.id.id28), findViewById(R.id.id29),
            findViewById(R.id.id30),findViewById(R.id.id31),findViewById(R.id.id32), findViewById(R.id.id33), findViewById(R.id.id34) , findViewById(R.id.id35),
            findViewById(R.id.id36), findViewById(R.id.id37),findViewById(R.id.id38), findViewById(R.id.id39), findViewById(R.id.id40), findViewById(R.id.id41),
            findViewById(R.id.id42), findViewById(R.id.id43), findViewById(R.id.id44), findViewById(R.id.id45),findViewById(R.id.id46) , findViewById(R.id.id47),
            findViewById(R.id.id48))
        //legge iconer for knappene + klokkeslett
        set_scrollView(liste)

        //gjøre dem clickbare for å få info

        for(item in liste){
            item.setOnClickListener {
                var plass = liste.indexOf(item)
                get_info(plass)

            }
        }
    }
 fun set_scrollView(liste: List<Button>){
    for(nr in 0..47){
        val tid =  data?.moxForecast?.get(nr)?.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin
        val tiden = tid!!.split("T" ,":")
        liste[nr].text=  tiden[1].toString()
    }

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


}


}


